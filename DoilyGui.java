import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.Graphics2D;
import java.awt.event.*;

public class DoilyGui extends JFrame {

    ArrayList<BufferedImage> galleryArray = new ArrayList<BufferedImage>();
    ArrayList<Line> lines = new ArrayList<Line>();

    int currentPicture = 0;
    int sectors = 16;
    int numberOfPictures = 0;
    boolean sectorsDrawn = true;

    Border blackline = BorderFactory.createLineBorder(Color.darkGray);

    // a temporary line is used to store the current line being drawn

    Line tempLine = new Line(true, Color.WHITE, 4);

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Look and feel not set!");
        }

        DoilyGui frame = new DoilyGui();
        frame.initialise();
    }

    public void initialise() {

        // method to set up doily

        this.setTitle("Doily Maker");
        Container contentPane = this.getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1800, 900);
        setResizable(false);
        setLocationRelativeTo(null);

        // making the components

        JButton save = new JButton("Save");
        JButton undo = new JButton("Undo");
        JButton clear = new JButton("Clear");

        JLabel numSectionsName = new JLabel("Number of Sectors:");
        JLabel penSize = new JLabel("Pen Size:");
        JButton penColorButton = new JButton("Pen Colour");

        JButton delete = new JButton("Delete");
        BasicArrowButton left = new BasicArrowButton(BasicArrowButton.WEST);
        BasicArrowButton right = new BasicArrowButton(BasicArrowButton.EAST);

        JCheckBox showSectors = new JCheckBox("Show Sectors", true);
        JCheckBox showReflected = new JCheckBox("Reflect", true);

        JPanel upperPanel = new JPanel();
        Display display = new Display();
        GalleryPanel gallery = new GalleryPanel();

        upperPanel.setLayout(new GridLayout(1, 2, 20, 20));
        upperPanel.add(display);
        upperPanel.add(gallery);

        contentPane.add(upperPanel, BorderLayout.CENTER);

        JPanel control = new JPanel();
        contentPane.add(control, BorderLayout.SOUTH);
        control.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        JPanel saveClearUndo = new JPanel();
        saveClearUndo.setLayout(new GridLayout(1,3, 10, 10));

        JPanel pen = new JPanel();
        pen.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        JPanel showLineReflected = new JPanel();
        showLineReflected.setLayout(new GridLayout(2, 1, 30, 10));

        JPanel sections = new JPanel();
        sections.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        JPanel galleryControls = new JPanel();
        galleryControls.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 10));

        JSlider penSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 4);
        penSizeSlider.setMinorTickSpacing(1);
        penSizeSlider.setMajorTickSpacing(10);
        penSizeSlider.setPaintTicks(true);
        penSizeSlider.setPaintLabels(true);
        penSizeSlider.setPaintTrack(true);

        JSlider numberOfSectors = new JSlider(JSlider.HORIZONTAL, 3, 32, 16);
        numberOfSectors.setMinorTickSpacing(1);
        numberOfSectors.setMajorTickSpacing(8);
        numberOfSectors.setPaintTicks(true);
        numberOfSectors.setPaintLabels(true);
        numberOfSectors.setPaintTrack(true);

        penColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempLine.setPenColor(JColorChooser.showDialog(null, "Pick Your Colour", tempLine.penColor));
            }
        });

        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (lines.size() > 0) {
                    lines.remove(lines.size() - 1);
                    repaint();
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lines.removeAll(lines);
                repaint();
            }
        });

        // action listener for the save button

        save.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                // prevents saving of more than 12 images

                if (numberOfPictures < 2) {

                    // keeps a track of the number of images in the gallery
                    // saves the display graphics as a buffered image in the gallery array

                    numberOfPictures++;
                    BufferedImage image = new BufferedImage(display.getWidth(), display.getHeight(),
                            BufferedImage.TYPE_INT_RGB);
                    Graphics2D imageGraphics = image.createGraphics();
                    display.paintAll(imageGraphics);
                    galleryArray.add(image);
                    currentPicture++;

                } else {

                    // if you try and save more than 12 images, you recieve an error message

                    JFrame frame = new JFrame("Error");
                    JOptionPane.showMessageDialog(frame, "You cannot save more than 12 images in a session", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                }

                // updates the gallery to display the most recently saved image

                gallery.repaint();

            }
        });

        penSizeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                tempLine.setPenSize(penSizeSlider.getValue());
            }
        });

        numberOfSectors.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sectors = numberOfSectors.getValue();
                repaint();
            }
        });

        showSectors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JCheckBox sectorDraw = (JCheckBox) e.getSource();

                if (sectorDraw.isSelected())
                    sectorsDrawn = true;
                else
                    sectorsDrawn = false;

                repaint();
            }
        });

        showReflected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JCheckBox reflect = (JCheckBox) e.getSource();

                if (reflect.isSelected())
                    tempLine.setReflected(true);
                else
                    tempLine.setReflected(false);

                repaint();
            }
        });

        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (1 < currentPicture)
                    currentPicture--;

                repaint();
            }
        });

        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (currentPicture < galleryArray.size())
                    currentPicture++;

                repaint();
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // checks to see if there is an image to delete from the gallery

                JFrame frame = new JFrame("Error");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                if (numberOfPictures == 0)
                    JOptionPane.showMessageDialog(frame, "No images in gallery to delete.", "Warning",
                            JOptionPane.WARNING_MESSAGE);

                else {

                    // checks to make sure that the delete button wasnt intentionally clicked (are
                    // you sure)

                    int reply = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to delete this picture?\nThis action cannot be undone.", "Warning",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (reply == JOptionPane.YES_OPTION) {

                        if (numberOfPictures > 0) {

                            galleryArray.remove(currentPicture - 1);
                            numberOfPictures--;
                            currentPicture = numberOfPictures;

                            repaint();
                            JOptionPane.showMessageDialog(null, "Picture deleted");
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        }
                    }
                }
            }
        });

        saveClearUndo.add(save);
        saveClearUndo.add(undo);
        saveClearUndo.add(clear);

        sections.add(numSectionsName);
        sections.add(numberOfSectors);
        
        pen.add(penSize);
        pen.add(penSizeSlider);
        pen.add(penColorButton);

        showLineReflected.add(showReflected);
        showLineReflected.add(showSectors);

        galleryControls.add(left);
        galleryControls.add(delete);
        galleryControls.add(right);

        control.add(saveClearUndo);
        control.add(sections);
        control.add(pen);
        control.add(showLineReflected);
        control.add(galleryControls);

        setVisible(true);
    }

    // this class is used to display the doilys as a buffered image

    class GalleryPanel extends JPanel {

        public GalleryPanel() {
            setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT));
        }

        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D gallery = (Graphics2D) g;

            // displayes the image from the array of saved doily's (buffered images)

            if (numberOfPictures > 0)
                gallery.drawImage(galleryArray.get(currentPicture - 1), null, 0, 0);

            else {
                // when theres no images saved in the gallery, the words gallery are displayed

                Font font = new Font("Arial", Font.BOLD, 40);

                gallery.setFont(font);

                FontMetrics metrics = g.getFontMetrics(font);
                // Determine the X coordinate for the text
                int x = this.getX() + (this.getWidth() - metrics.stringWidth("GALLERY")) / 2;
                // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
                int y = this.getY() + ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                // Set the font
                gallery.setFont(font);
                // Draw the String
                gallery.drawString("GALLERY", x, y);
            }
        }
    }
}
