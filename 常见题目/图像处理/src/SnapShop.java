import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

/**
 * A PhotoShop like application for filtering images
 *
 * @author Richard Dunn
 * @author Barbara Goldner
 * @author Dan Jinguji
 * @version April 2002, 2013
 */
public class SnapShop {
    protected JFrame win;
    private FileLoader fl;
    private FilterButtons fb;
    private FilterButtons fb2;
    private FilterButtons fb3;
    private FilterButtons fb4;
    private FilterButtons fb5;
    private FilterButtons fb6;
    private FilterButtons fb7;
    private FilterButtons fb8;
    private FilterButtons fb9;
    private ImagePanel ip;
    private RenderingDialog rd;

    /**
     * Constructor for objects of class SnapShop
     */
    public SnapShop() {
        win = new JFrame("CSC 143 - SnapShop");

        // Create all the necessary components and add them to the frame
        ip = new ImagePanel(this);
        win.add(ip, BorderLayout.CENTER);

        fl = new FileLoader(this);
        win.add(fl, BorderLayout.NORTH);

       /* fb = new FilterButtons(this);
        win.add(fb, BorderLayout.WEST);
        fb2 = new FilterButtons(this);
        win.add(fb2,BorderLayout.EAST);*/

        fb = new FilterButtons(this);
        fb2 = new FilterButtons(this);
        fb3 = new FilterButtons(this);
        fb4 = new FilterButtons(this);
        fb5 = new FilterButtons(this);
        fb6 = new FilterButtons(this);
        fb7 = new FilterButtons(this);
        fb8 = new FilterButtons(this);
        fb9 = new FilterButtons(this);
        Panel all = new Panel();
        all.setLayout(new FlowLayout(1, 5, 5));
        all.add(fb);
        all.add(fb2);
        all.add(fb3);
        all.add(fb4);
        all.add(fb5);
        all.add(fb6);
        all.add(fb7);
        all.add(fb8);
        all.add(fb9);

        win.add(all, BorderLayout.WEST);

        rd = new RenderingDialog(win);

        SnapShopConfiguration.configure(this);

        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win.pack();
        win.setVisible(true);
    }

    /**
     * Add a filter to the SnapShop GUI to flip the image horizontally.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addFlipHorizontalFilter(Filter f) {
        fb.addFilter(f, "Flip Horizontal");
    }

    public void addFilpVerticalFilter(Filter f) {
        fb2.addFilter(f, "Flip Vertical");
    }

    /**
     * Add a filter to the SnapShop GUI to flip the image vertically.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addFlipVerticalFilter(Filter f) {
        fb.addFilter(f, "Flip Vertical");
    }

    /**
     * Add a filter to the SnapShop GUI to convert to gray scale 1.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addGrayScale1Filter(Filter f) {
        fb.addFilter(f, "Gray Scale 1");
    }

    /**
     * Add a filter to the SnapShop GUI to convert to gray scale 2.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addGrayScale2Filter(Filter f) {
        fb.addFilter(f, "Gray Scale 2");
    }

    /**
     * Add a filter to the SnapShop GUI to apply Gaussian smoothing.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addGaussianFilter(Filter f) {
        fb.addFilter(f, "Gaussian");
    }

    /**
     * Add a filter to the SnapShop GUI to apply a Laplacian transform.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addLaplacianFilter(Filter f) {
        fb.addFilter(f, "Laplacian");
    }

    /**
     * Add a filter to the SnapShop GUI to apply unsharp masking.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addUnsharpMaskingFilter(Filter f) {
        fb.addFilter(f, "Unsharp Masking");
    }

    /**
     * Add a filter to the SnapShop GUI to apply an "edgy" transform.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addEdgyFilter(Filter f) {
        fb.addFilter(f, "\"Edgy\"");
    }

    /**
     * Add a filter to the SnapShop GUI to rotate the image clockwise.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addRotateClockwiseFilter(Filter f) {
        fb.addFilter(f, "Rotate Clockwise");
    }

    /**
     * Add a filter to the SnapShop GUI to rotate the image counter-
     * clockwise.
     * Creates a button and links it to the filter.
     *
     * @param f The filter to apply
     */
    public void addRotateCounterClockwiseFilter(Filter f) {
        fb.addFilter(f, "Rotate Counter-Clockwise");
    }

    /**
     * Set the default filename to appear in the box
     *
     * @param filename The filename
     */
    public void setDefaultFilename(String filename) {
        fl.setDefaultFilename(filename);
    }

    /**
     * Open a SnapShop
     */
    public static void main(String[] args) {
        SnapShop s = new SnapShop();
    }

////////////////////////////////////////////////////////////////////
//  EVERYTHING BELOW THIS POINT CAN BE INGORED ... UNLESS YOU WANT TO
//  ENABLE THE ROTATION OF NON-SQUARE IMAGES.  FEEL FREE TO LOOK AT
//  IT IF YOU WISH.  YOU DO NOT NEED TO KNOW THIS IN ORDER TO SOLVE
//  THE GIVEN PROBLEM.  MOST OF THIS MATERIAL WILL BE COVERED IN THE
//  WEEKS AHEAD.
//
//  PROCEED WITH CAUTION...
////////////////////////////////////////////////////////////////////

    /**
     * Displays a wait/dialog frame
     */
    protected void showWaitDialog() {
        rd.pack();
        rd.setVisible(true);
    }

    /**
     * Hides the wait/dialog frame
     */
    protected void hideWaitDialog() {
        rd.setVisible(false);
    }

    /**
     * @returns the ImagePanel
     */
    protected ImagePanel getImagePanel() {
        return ip;
    }


    // Private Inner Class
    // Loads a file specified by user
    private class FileLoader extends JPanel implements ActionListener {
        private JLabel lbl;
        private JTextArea filenameBox;
        private ImagePanel ip;
        private SnapShop snapshop;
        private JFileChooser pick;
        private MouseListener mouse = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (pick.showOpenDialog(snapshop.win) == JFileChooser.APPROVE_OPTION) {
                    String filename = pick.getSelectedFile().getAbsolutePath();
                    filenameBox.setText(filename);
                    loadFile(filename);
                }
            }
        };

        /* create a new FileLoader
         * @param s the SnapShop we're working with
         */
        public FileLoader(SnapShop s) {
            super(new FlowLayout());

            pick = new JFileChooser();
            pick.setFileFilter(new FileNameExtensionFilter("Image files",
                    "jpg", "gif", "png", "bmp"));

            snapshop = s;
            this.ip = s.getImagePanel();

            lbl = new JLabel("Click to enter file name: ");
            lbl.addMouseListener(mouse);
            add(lbl);

            filenameBox = new JTextArea(1, 50);
            filenameBox.addMouseListener(mouse);
            add(filenameBox);

            JButton loadButton = new JButton("Load");
            loadButton.addActionListener(this);
            add(loadButton);
        }

        // what to do when the load button is clicked
        public void actionPerformed(ActionEvent e) {
            loadFile(filenameBox.getText());
        }

        private void loadFile(String filename) {
            try {
                ip.loadImage(filename);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(snapshop.win,
                        "Could not open file",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        public void setDefaultFilename(String filename) {
            filenameBox.setText(filename);
        }
    }

    // Private Inner class
    // Creates buttons for the user specified filters
    // And handles event when user clicks on button
    private class FilterButtons extends JPanel {
        private JPanel buttons;
        private SnapShop snapshop;
        private ImagePanel ip;

        public FilterButtons(SnapShop s) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            buttons = new JPanel(new GridLayout(9, 1));
            snapshop = s;
            ip = s.getImagePanel();
            add(buttons);
            add(Box.createVerticalGlue());
        }

        public void addFilter(Filter f, String description) {
            JButton filterButton = new JButton(description);
            filterButton.addActionListener(new FilterButtonListener(this, f));
            buttons.add(filterButton);
            snapshop.win.pack();
        }

        public void applyFilter(Filter f) {
            ip.applyFilter(f);
        }

        // inner, inner class
        private class FilterButtonListener implements ActionListener {
            private FilterButtons fb;
            private Filter f;

            public FilterButtonListener(FilterButtons fb, Filter f) {
                this.fb = fb;
                this.f = f;
            }

            public void actionPerformed(ActionEvent e) {
                fb.applyFilter(f);
            }
        }
    }

    // Inner class
    // Location for displaying the PixelImage
    private class ImagePanel extends JPanel {
        private BufferedImage bi;
        private SnapShop snapshop;

        public ImagePanel(SnapShop s) {
            bi = null;
            snapshop = s;
        }

        public void loadImage(String filename) {
            Image img = Toolkit.getDefaultToolkit().getImage(filename);
            try {
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(img, 0);
                tracker.waitForID(0);
            } catch (Exception e) {
            }
            int width = img.getWidth(this);
            int height = img.getHeight(this);
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D biContext = bi.createGraphics();
            biContext.drawImage(img, 0, 0, null);
            setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
            revalidate();
            snapshop.win.pack();
            snapshop.win.repaint();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bi != null) {
                g.drawImage(bi, 0, 0, this);
            }
        }

        // Here's where the work gets done
        // apply the filter to the PixelImage
        public void applyFilter(Filter f) {
            if (bi == null) {
                return;
            }
            PixelImage newImage = new PixelImage(bi);
            snapshop.showWaitDialog();
            try {
                f.filter(newImage);
            } catch (IllegalStateException e) {
                JOptionPane.showMessageDialog(win,
                        e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            snapshop.hideWaitDialog();
            bi = newImage.getImage();
            repaint();
        }
    }

    // Private inner class
    // a wait/dialog box to tell user that something is actually happening
    private class RenderingDialog extends JFrame {
        public RenderingDialog(JFrame parent) {
            super("Please Wait");
            Point p = parent.getLocation();
            setLocation(p.x + 100, p.y + 100);
            this.getContentPane().add(new JLabel("Applying filter, please wait..."), BorderLayout.CENTER);
        }
    }

}
