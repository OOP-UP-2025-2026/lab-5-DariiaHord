package ua.opnu.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class DrawFrame extends JFrame {

    private enum Tool { RECT, CIRCLE, ELLIPSE }

    private final List<Shape> shapes = new ArrayList<>();
    private Shape previewShape = null;
    private Point startPoint = null;
    private Tool currentTool = Tool.RECT;

    public DrawFrame(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton rectBtn = new JButton("Rectangle");
        JButton circleBtn = new JButton("Circle");
        JButton ellipseBtn = new JButton("Ellipse");
        JButton clearBtn = new JButton("Clear");

        rectBtn.addActionListener(e -> currentTool = Tool.RECT);
        circleBtn.addActionListener(e -> currentTool = Tool.CIRCLE);
        ellipseBtn.addActionListener(e -> currentTool = Tool.ELLIPSE);
        clearBtn.addActionListener(e -> { shapes.clear(); previewShape = null; repaint(); });

        toolbar.add(rectBtn);
        toolbar.add(circleBtn);
        toolbar.add(ellipseBtn);
        toolbar.add(clearBtn);

        DrawingPanel canvas = new DrawingPanel();
        canvas.setBackground(Color.WHITE);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(toolbar, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        setVisible(true);
    }

    private class DrawingPanel extends JPanel {
        public DrawingPanel() {
            MouseAdapter adapter = new MouseAdapter() {
                @Override public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint(); previewShape = null;
                }
                @Override public void mouseDragged(MouseEvent e) {
                    if (startPoint == null) return;
                    Point end = e.getPoint();
                    int x = Math.min(startPoint.x, end.x);
                    int y = Math.min(startPoint.y, end.y);
                    int w = Math.abs(startPoint.x - end.x);
                    int h = Math.abs(startPoint.y - end.y);

                    switch (currentTool) {
                        case RECT:    previewShape = new Rectangle2D.Double(x, y, w, h); break;
                        case CIRCLE:  { int d = Math.max(w, h); previewShape = new Ellipse2D.Double(x, y, d, d); break; }
                        case ELLIPSE: previewShape = new Ellipse2D.Double(x, y, w, h); break;
                    }
                    repaint();
                }
                @Override public void mouseReleased(MouseEvent e) {
                    if (previewShape != null) { shapes.add(previewShape); previewShape = null; repaint(); }
                    startPoint = null;
                }
            };
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (Shape s : shapes) g2.draw(s);
            if (previewShape != null) {
                Stroke old = g2.getStroke();
                float[] dash = {5f,5f};
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
                g2.draw(previewShape);
                g2.setStroke(old);
            }
            g2.dispose();
        }
    }
}
