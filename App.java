import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    Button[][] Buttons = new Button[5][5];
    boolean isXTurn = true;
    Label turnDisplay = new Label("X's turn");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) {
        GridPane board = new GridPane();

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Button square = new Button();
                square.setMinSize(60, 60);

                int currentRow = row;
                int currentCol = col;

                square.setOnAction(_ -> handleClick(square, currentRow, currentCol));

                Buttons[row][col] = square;
                board.add(square, col, row);
            }
        }

        VBox layout = new VBox(15, turnDisplay, board);
        Scene scene = new Scene(layout, 400, 450);

        mainWindow.setTitle("5x5 Tic Tac Toe");
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    private void handleClick(Button clickedButton, int row, int col) {
        if (!clickedButton.getText().equals("")) {
            return; 
        }

        if (isXTurn) {
            clickedButton.setText("X");
            turnDisplay.setText("O's turn");
        } else {
            clickedButton.setText("O");
            turnDisplay.setText("X's turn");
        }

        if (checkWinner(row, col)) {
            String winner = isXTurn ? "X" : "O";
            showAlert(winner + " wins!");
            resetGame();
        } else if (checkTie()) {
            showAlert("It's a tie!");
            resetGame();
        } else {
            isXTurn = !isXTurn;
        }
    }

    private boolean checkWinner(int row, int col) {
        String symbol = isXTurn ? "X" : "O";

        
        int horizontalCount = 0;
        for (int c = 0; c < 5; c++) {
            if (Buttons[row][c].getText().equals(symbol)) {
                horizontalCount++;
            } else {
                horizontalCount = 0;
            }
            if (horizontalCount == 5) return true;
        }

        
        int verticalCount = 0;
        for (int r = 0; r < 5; r++) {
            if (Buttons[r][col].getText().equals(symbol)) {
                verticalCount++;
            } else {
                verticalCount = 0;
            }
            if (verticalCount == 5) return true;
        }

        
        int diag1Count = 0;
        for (int i = -4; i <= 4; i++) {
            int r = row + i;
            int c = col + i;
            if (r >= 0 && r < 5 && c >= 0 && c < 5) {
                if (Buttons[r][c].getText().equals(symbol)) {
                    diag1Count++;
                } else {
                    diag1Count = 0;
                }
                if (diag1Count == 5) return true;
            }
        }

        
        int diag2Count = 0;
        for (int i = -4; i <= 4; i++) {
            int r = row + i;
            int c = col - i;
            if (r >= 0 && r < 5 && c >= 0 && c < 5) {
                if (Buttons[r][c].getText().equals(symbol)) {
                    diag2Count++;
                } else {
                    diag2Count = 0;
                }
                if (diag2Count == 5) return true;
            }
        }

        return false;
    }

    private boolean checkTie() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (Buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Buttons[row][col].setText("");
            }
        }
        isXTurn = true;
        turnDisplay.setText("X's turn");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
