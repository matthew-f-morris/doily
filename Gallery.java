import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.image.BufferedImage;

public class Gallery extends JPanel {

    private int numberOfPictures = 0;
    private int currentPicture = 0;
    private List<BufferedImage> galleryArray = new ArrayList<BufferedImage>();

    public Gallery(int numPics) {

        setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT));
        this.numberOfPictures = numPics;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D gallery = (Graphics2D) g;

        if (numberOfPictures > 0)
            gallery.drawImage(galleryArray.get(currentPicture - 1), null, 0, 0);

        else {

            String str = "Gallery is Empty";
            Font font = new Font("Arial", Font.BOLD, 40);
            FontMetrics metrics = gallery.getFontMetrics(font);

            int x = (this.getWidth() - metrics.stringWidth(str)) / 2;
            int y = ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

            gallery.setFont(font);
            gallery.drawString(str, x, y);
        }
    }
}