package GameComponents.Camera;

import java.awt.Point;

public class GameCamera {
    private static GameCamera gc;
    private Point cameraPosition;

    public GameCamera(){
        cameraPosition = new Point(0, 0);
    }
    public static GameCamera camera(){
        if (gc == null)
            gc = new GameCamera();

        return gc;
    }

    public void moveCameraX(int x){
        moveCamera(new Point(x, 0));
    }
    public void moveCameraY(int y){
        moveCamera(new Point(0, y));
    }
    public void moveCamera(int x, int y){
        moveCamera(new Point(x, y));
    }

    public void moveCamera(Point delta){
        cameraPosition = new Point(cameraPosition.x - delta.x, cameraPosition.y - delta.y);
    }

    public void setCameraPositionX(int x){
        setCameraPosition(new Point(x, getCameraPosition().y));
    }
    public void setCameraPositionY(int y){
        setCameraPosition(new Point(getCameraPosition().x, y));
    }
    public void setCameraPosition(int x, int y){
        setCameraPosition(new Point(x, y));
    }
    public void setCameraPosition(Point pos){
        cameraPosition.setLocation(pos);
    }

    public Point getCameraPosition(){
        return cameraPosition;
    }

    public String getCameraPositionToString(){
        return "Camera at: " + getCameraPosition().x + ", " + getCameraPosition().y;
    }
}
