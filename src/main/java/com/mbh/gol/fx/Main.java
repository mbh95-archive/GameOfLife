package com.mbh.gol.fx;

import com.mbh.gol.GameOfLife;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private GameOfLife gol = new GameOfLife();

    private boolean running = false;

    private double cx, cy;
    private double pxPerSquare = 16;

    private boolean pen = true;

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane pane;

    @FXML
    private ToggleButton startStopButton;

    public static void main(String[] args) {
        launch(args);
    }

    private double screenToWorldX(double screenX) {
        return cx + (screenX - canvas.getWidth() / 2) / pxPerSquare;
    }

    private double screenToWorldY(double screenY) {
        return cy + (screenY - canvas.getHeight() / 2) / pxPerSquare;
    }

    private double worldToScreenX(double worldX) {
        return (canvas.getWidth() / 2) + (worldX - cx) * pxPerSquare;
    }

    private double worldToScreenY(double worldY) {
        return (canvas.getHeight() / 2) + (worldY - cy) * pxPerSquare;
    }

    @FXML
    void mouseDown(MouseEvent event) {
        int mouseRow = (int) Math.floor(screenToWorldY(event.getY()));
        int mouseCol = (int) Math.floor(screenToWorldX(event.getX()));
        pen = !gol.test(mouseRow, mouseCol);
        gol.set(mouseRow, mouseCol, pen);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        gol.set((int) Math.floor(screenToWorldY(event.getY())),
                (int) Math.floor(screenToWorldX(event.getX())),
                pen);
    }

    @FXML
    public void onScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() > 0) {
            pxPerSquare *= 1.1;
            pxPerSquare = Math.min(pxPerSquare, 1000);
        } else if (scrollEvent.getDeltaY() < 0) {
            pxPerSquare *= 0.9;
            pxPerSquare = Math.max(pxPerSquare, 1);
        }
    }

    @FXML
    void startStop() {
        this.running = !this.running;
        this.startStopButton.setText(this.running ? "Pause" : "Start");
    }

    @FXML
    void initialize() {
        assert canvas != null : "fx:id=\"canvas\" not injected: check your FXML file 'gol.fxml'.";
        assert pane != null : "fx:id=\"pane\" not injected: check your FXML file 'gol.fxml'.";

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                ae -> {
                    if (running) {
                        gol.step();
                    }
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                    gc.setFill(Color.WHITE);
                    int minC = (int) Math.floor(screenToWorldX(0));
                    int minR = (int) Math.floor(screenToWorldY(0));
                    int maxC = (int) Math.floor(screenToWorldX(canvas.getWidth()));
                    int maxR = (int) Math.floor(screenToWorldY(canvas.getHeight()));
                    gol.getLiveCells().stream()
                            .filter(cell -> cell.r() >= minR
                                    && cell.r() <= maxR
                                    && cell.c() >= minC
                                    && cell.c() <= maxC)
                            .forEach(onScreenLiveCell ->
                                    gc.fillRect(worldToScreenX(onScreenLiveCell.c()),
                                            worldToScreenY(onScreenLiveCell.r()),
                                            pxPerSquare, pxPerSquare));
                }
        );

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gol.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.show();
    }
}
