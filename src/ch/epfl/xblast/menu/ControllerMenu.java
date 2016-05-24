package ch.epfl.xblast.menu;

public final class ControllerMenu {
    private ViewMenu view;
    private ModelMenu model;
    
    public ControllerMenu(ViewMenu view,ModelMenu model){
        this.view=view;
        this.model=model;
        setMainMenu(view);
        view.frame.setVisible(true);
    }
    
    public void addActionListener(ViewMenu view){
        model.join.addActionListener(e->setJoin(view));
    }
    
    public void setMainMenu(ViewMenu view){
        view.frame.getContentPane().removeAll();
        view.frame.add(view.createMenuView(model));
        model.join.addActionListener(e -> setJoin(view));
        view.frame.validate();
        view.frame.repaint();
    }
    
    public void setJoin(ViewMenu view){
        view.frame.getContentPane().removeAll();
        view.frame.add(view.createJoinMenu(model));
        model.backJoin.addActionListener(e -> setMainMenu(view));
        view.frame.validate();
        view.frame.repaint();
    }
    
}
