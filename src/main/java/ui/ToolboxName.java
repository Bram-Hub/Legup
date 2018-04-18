package ui;

public enum ToolboxName {
    ZOOM_IN, ZOOM_OUT, NORMAL_ZOOM, BEST_FIT, ANNOTATIONS;

    /**
     * Gets the String representation of the ToolboxName enum
     *
     * @return String representing the enum
     */
    public String toString()
    {
        String str = super.toString();
        str = str.replace("_", " ");
        str = str.toLowerCase();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == ' ')
            {
                str = str.substring(0, i + 1) +
                        str.substring(i + 1, i + 2).toUpperCase() +
                        str.substring(i + 2);
            }
        }
        return str;
    }
}
