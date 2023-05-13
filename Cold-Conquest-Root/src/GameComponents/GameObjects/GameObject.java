package GameComponents.GameObjects;

public abstract class GameObject {
    //Includes all objects which only excludes IU
    public abstract void OnStart();
    public abstract void OnUpdate();
    public abstract void LateUpdate();
}
