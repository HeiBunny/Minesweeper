import java.awt.*;

public class Square {

    private boolean isMine, isRevealed, flag;  // Is this a mine?  Is this revealed?
    private int numNeighborMines;  // how many mines are around this square
    private int r, c;  // what index this Square is in board
    private Square[][] board; // the entire board

    public Square(boolean isMine, int r, int c, Square[][] board) {
        this.isMine = isMine;
        this.r = r;
        this.c = c;
        this.isRevealed = false;
        this.board = board;
        numNeighborMines = 0;
        flag = false;
    }

    public void revealCell(){
        if(!isRevealed && !isFlag()){
            board[r][c].isRevealed = true;
                if(board[r][c].numNeighborMines == 0) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if(isInBounds(r + 1 - i, c + 1 - j)) {
                                if (board[r + 1 - i][c + 1 - j].numNeighborMines == 0) {
                                    board[r + 1 - i][c + 1 - j].revealCell();
                                } else {
                                    board[r + 1 - i][c + 1 - j].isRevealed = true;
                                }
                            }
                    }
                }
            }
        }

    }

    // Counts how many Squares around this have mines, updates numNeighborMines
    public void calcNeighborMines() {
        int count = 0;

        if (isInBounds(r-1,c-1) && board[r-1][c-1].isMine)
            count++;
        if (isInBounds(r-1,c) && board[r-1][c].isMine )
            count++;
        if (isInBounds(r-1,c+1) && board[r-1][c+1].isMine)
            count++;
        if (isInBounds(r,c-1) && board[r][c-1].isMine)
            count++;
        if (isInBounds(r,c+1) && board[r][c+1].isMine)
            count++;
        if (isInBounds(r+1,c-1) && board[r+1][c-1].isMine)
            count++;
        if (isInBounds(r+1,c) && board[r+1][c].isMine)
            count++;
        if (isInBounds(r+1,c+1) && board[r+1][c+1].isMine)
            count++;

        numNeighborMines = count;
    }



    public void draw(Graphics2D g2){
        int size = MineSweeper.SIZE;

        if(isRevealed) {
            if(isMine) {
                g2.setColor(Color.RED);
                g2.fillRect(c * size, r * size, size, size);
            }else{
                g2.setColor(Color.BLACK);
                g2.fillRect(c * size, r * size, size, size);
                g2.setColor(Color.YELLOW);
                g2.setFont(new Font("CHALKDUSTER", Font.BOLD, 24));
                if(numNeighborMines != 0) {
                    g2.drawString("" + numNeighborMines, c * size + 12, r * size + 25);
                }
            }
        }else if(flag){
            g2.setColor(Color.BLUE);
            g2.fillRect(c * size, r * size, size, size);
        }
        else{
            g2.setColor(Color.GRAY);
            g2.fillRect(c * size, r * size, size, size);
        }
        g2.setColor(Color.BLACK);
        g2.drawRect(c * size, r * size, size, size);
    }

    public boolean isWrongFlag(Square n){
        if (n.isMine && n.isFlag()){
            return true;
        }
        return false;
    }

    public boolean isInBounds(int row, int col){
        return row < board.length && row > -1 && col < board[0].length && col > -1;
    }

    public void click(){
        isRevealed = true;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getNumNeighborMines() {
        return numNeighborMines;
    }
}