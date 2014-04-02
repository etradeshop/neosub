package org.jvnet.substance.watermark;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * Simple implementation of {@link SubstanceWatermark}, drawing cross hatches
 * as watermark. This implementation is inspired by Office 12 background.
 *
 * @author Kirill Grouchnikov
 */
public class SubstanceCrosshatchWatermark implements SubstanceWatermark {
    /**
     * Watermark image (screen-sized).
     */
    private static Image watermarkImage = null;

    /*
      * (non-Javadoc)
      *
      * @see org.jvnet.substance.watermark.SubstanceWatermark#drawWatermarkImage(java.awt.Graphics,
      *      int, int, int, int)
      */
    public void drawWatermarkImage(Graphics graphics, Component c, int x,
                                   int y, int width, int height) {
        int dx = c.getLocationOnScreen().x;
        int dy = c.getLocationOnScreen().y;
        graphics.drawImage(SubstanceCrosshatchWatermark.watermarkImage, x, y, x
                + width, y + height, x + dx, y + dy, x + dx + width, y + dy
                + height, null);
    }

    /*
      * (non-Javadoc)
      *
      * @see org.jvnet.substance.watermark.SubstanceWatermark#updateWatermarkImage()
      */
    public boolean updateWatermarkImage() {
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenDim.width;
        int screenHeight = screenDim.height;
        SubstanceCrosshatchWatermark.watermarkImage = SubstanceImageCreator
                .getBlankImage(screenWidth, screenHeight);

        Graphics2D graphics = (Graphics2D) SubstanceCrosshatchWatermark
                .watermarkImage
                .getGraphics().create();
        Color stampColorDark = SubstanceLookAndFeel.getColorScheme().isDark() ?
                new Color(
                        0, 0, 0, 35)
                : new Color(0, 0, 0, 20);
        Color stampColorAll = SubstanceLookAndFeel.getColorScheme().isDark() ?
                new Color(
                        255, 255, 255, 10)
                : new Color(0, 0, 0, 10);
        Color stampColorLight = SubstanceLookAndFeel.getColorScheme().isDark() ?
                new Color(
                        255, 255, 255, 20)
                : new Color(255, 255, 255, 128);

        graphics.setColor(stampColorAll);
        graphics.fillRect(0, 0, screenWidth, screenHeight);

        BufferedImage tile = SubstanceImageCreator.getBlankImage(4, 4);
        tile.setRGB(0, 0, stampColorDark.getRGB());
        tile.setRGB(2, 2, stampColorDark.getRGB());
        tile.setRGB(0, 1, stampColorLight.getRGB());
        tile.setRGB(2, 3, stampColorLight.getRGB());

        for (int row = 0; row < screenHeight; row += 4) {
            for (int col=0; col<screenWidth; col += 4) {
                graphics.drawImage(tile, col, row, null);
            }
        }
        graphics.dispose();
        return true;
    }

    /*
      * (non-Javadoc)
      *
      * @see org.jvnet.substance.watermark.SubstanceWatermark#getDisplayName()
      */
    public String getDisplayName() {
        return SubstanceCrosshatchWatermark.getName();
    }

    public static String getName() {
        return "Crosshatch";
    }

    /* (non-Javadoc)
      * @see org.jvnet.substance.watermark.SubstanceWatermark#isDependingOnTheme()
      */
    public boolean isDependingOnTheme() {
        return true;
    }
}
