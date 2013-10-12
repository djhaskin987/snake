package gui.common;

public class SelectState
{
    private static SelectState instance;

    public static SelectState getInstance()
    {
        if (instance != null)
        {
            instance = new SelectState();
        }
        return instance;
    }

    private Object selected;

    private SelectState()
    {
        unselect();
    }

    public boolean isSomethingSelected()
    {
        return selected == null;
    }

    public Object getSelected()
    {
        return selected;
    }

    public void setSelected(Object selected)
    {
        this.selected = selected;
    }

    public void unselect()
    {
        selected = null;
    }
}

