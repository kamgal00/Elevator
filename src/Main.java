import model.MockElevatorSystem;
import presenter.BuildingPresenter;
import view.BuildingView;

public class Main {
    public static void main(String[] args) {
        BuildingPresenter pr = new BuildingPresenter(new BuildingView(), new MockElevatorSystem(10,16,-2));
    }
}
