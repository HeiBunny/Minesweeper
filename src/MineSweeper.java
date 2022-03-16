import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Josh Liu

public class MineSweeper extends JPanel {
    private Square[][] board;
    private boolean gameOver, firstClick;
    private Point mLocation;

    public static final int SIZE = 40;

    public MineSweeper(int width, int height) {
        setSize(width, height);

        board = new Square[15][15];
        gameOver = false;
        mLocation = new Point();
        firstClick = true;

        //TODO Go to each index in board and assign a new Square object
        //     Each square should have a 25% chance of being a mine
        //  ALT: have a fixed number of squares contain mines.

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                    board[i][j] = new Square(false, i, j, board);
            }
        }

        // Here is a good spot to calc each Square's neighborMines
        // (after all squares are initialized-not in the above loops

        setupMouseListener();
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // TODO Go to each square in board and tell it to draw.
        for(Square[] n: board) {
            for (Square m : n) {
                m.draw(g2);
                }
            }

        g2.setColor(Color.BLACK);
        g2.fillRect(600,0, 75, 600);
        g2.setColor(Color.YELLOW);
        g2.fillRect(610,280,55,40);
    }

    public void setupMouseListener(){
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                int r = y / SIZE;
                int c = x / SIZE;

                if(firstClick){
                    firstClick = false;
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[i].length; j++) {
                            if((i < r-1 || i > r+1) || (j > c+1 || j < c-1)) {
                                int ranInt = (int) (Math.random() * 100);
                                if (ranInt < 25) {
                                    board[i][j] = new Square(true, i, j, board);
                                }
                            }
                        }
                    }
                    for(Square[] n: board){ 
                        for(Square m: n){
                            m.calcNeighborMines();
                        }
                    }
                }

                if(x > 610 && x < 665 && y < 320 && y > 280){
                    reset();
                }

                if(x > 0 && x < 600 && y > 0 && y < 600){
                if(!gameOver) {
                        board[r][c].revealCell();
                }
                if(board[r][c].isMine() && !board[r][c].isFlag()) {
                    gameOver = true;
                    for (Square[] n : board) {
                        for (Square m : n) {
                            if (m.isMine()) {
                                m.setRevealed(true);
                                }
                            }
                        }
                    }
                }

                repaint();
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                int r = mLocation.y/SIZE;
                int c = mLocation.x/SIZE;
                board[r][c].setFlag(!board[r][c].isFlag());

                repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
            mLocation = e.getPoint();
            }
        });
    }

    public void reset(){
        firstClick = true;
        gameOver = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Square(false, i, j, board);
            }
        }
    }

    //sets ups the panel and frame.  Probably not much to modify here.
    public static void main(String[] args) {
        JFrame window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 675, 600 + 22); //(x, y, w, h) 22 due to title bar.

        MineSweeper panel = new MineSweeper(675, 600);

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }
}