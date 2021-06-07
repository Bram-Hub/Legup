package edu.rpi.legup.ui;

import edu.rpi.legup.ui.lookandfeel.materialdesign.MaterialColors;
import edu.rpi.legup.ui.lookandfeel.materialdesign.MaterialFonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.Hashtable;

import static java.awt.BorderLayout.*;

public class DynamicView extends JPanel {

    private ScrollView scrollView;
    private JPanel zoomWrapper;
    private JPanel zoomer;
    private JLabel status;

    private static final Font ERROR_FONT = MaterialFonts.ITALIC;
    private static final Color ERROR_COLOR = MaterialColors.RED_700;

    private static final Font INFO_FONT = MaterialFonts.REGULAR;
    private static final Color INFO_COLOR = MaterialColors.GRAY_900;

    public DynamicView(ScrollView scrollView) {
        this.scrollView = scrollView;

        setLayout(new BorderLayout());

        add(scrollView, CENTER);
        add(setUpZoomer(), SOUTH);
    }

    private JPanel setUpZoomer() {
        zoomWrapper = new JPanel();
        try {
            zoomer = new JPanel();

            JLabel zoomLabel = new JLabel("100%");
            zoomLabel.setFont(MaterialFonts.getRegularFont(16f));

            JSlider zoomSlider = new JSlider(25, 400, 100);

            JButton plus = new JButton(new ImageIcon(ImageIO.read(ClassLoader.getSystemClassLoader().getResource("edu/rpi/legup/imgs/add.png"))));
            plus.setFont(MaterialFonts.getRegularFont(10f));
            plus.setPreferredSize(new Dimension(20, 20));
            plus.addActionListener((ActionEvent e) -> {
                zoomSlider.setValue(zoomSlider.getValue() + 25);
            });


            JButton minus = new JButton(new ImageIcon(ImageIO.read(ClassLoader.getSystemClassLoader().getResource("edu/rpi/legup/imgs/remove.png"))));
            minus.setPreferredSize(new Dimension(20, 20));
            minus.setFont(MaterialFonts.getRegularFont(10f));
            minus.addActionListener((ActionEvent e) -> {
                zoomSlider.setValue(zoomSlider.getValue() - 25);
            });
            this.scrollView.setWheelScrollingEnabled(true);
            /*this.scrollView.getViewport().addMouseWheelListener(new MouseAdapter() {
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if (e.isControlDown()) {
                        scrollView.zoom(e.getWheelRotation() * 2, e.getPoint());
                    } else {
                        scrollView.zoom(e.getWheelRotation(), e.getPoint());
                    }
                    zoomSlider.setValue((int) (scrollView.getScale() * 100));
                }
            });*/

            zoomSlider.setPreferredSize(new Dimension(160, 30));

            zoomSlider.addChangeListener((ChangeEvent e) -> {
                scrollView.zoomTo(zoomSlider.getValue() / 100.0);
                zoomLabel.setText(zoomSlider.getValue() + "%");
            });


            zoomSlider.setMajorTickSpacing(100);
            zoomSlider.setMinorTickSpacing(25);
            zoomSlider.setPaintTicks(true);

            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(0, new JLabel("25%"));
            labelTable.put(100, new JLabel("100%"));
            labelTable.put(400, new JLabel("400%"));
            zoomSlider.setLabelTable(labelTable);

            zoomer.setLayout(new FlowLayout());

            zoomer.add(minus);
            zoomer.add(zoomSlider);
            zoomer.add(plus);
            zoomer.add(zoomLabel);

            status = new JLabel();

            zoomWrapper.setLayout(new BorderLayout());
            zoomWrapper.add(status, WEST);
            zoomWrapper.add(zoomer, EAST);
        } catch (IOException e) {

        }
        return zoomWrapper;
    }

    public ScrollView getScrollView() {
        return this.scrollView;
    }

    public JPanel getZoomWrapper() {
        return this.zoomWrapper;
    }

    public JPanel getZoomer() {
        return this.zoomer;
    }

    public void updateInfo(String message) {
        status.setFont(INFO_FONT);
        status.setForeground(INFO_COLOR);
        status.setText(message);
    }

    public void updateError(String message) {
        status.setFont(ERROR_FONT);
        status.setForeground(ERROR_COLOR);
        status.setText(message);
    }

    public void resetStatus() {
        status.setText("");
    }
}
