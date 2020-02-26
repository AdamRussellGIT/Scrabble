public class Word {
    private int startRow;
    private int startColumn;
    private char direction;
    private String word;

    public Word(int startRow, int startColumn, char direction, String word) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.direction = direction;
        this.word = word;
    }

    public void clear(){
        setStartRow(0);
        setStartColumn(0);
        setWord(null);
    }

    public int getStartRow() {
        return this.startRow;
    }

    public int getStartColumn() {
        return this.startColumn;
    }

    public char getDirection() {
        return this.direction;
    }

    public String getWord() {
        return this.word;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
