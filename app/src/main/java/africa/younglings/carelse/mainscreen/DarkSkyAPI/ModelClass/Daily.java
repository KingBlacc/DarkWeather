package africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass;

public class Daily
{
    private String summary;

    private Data[] data;

    private String icon;

    public String getSummary ()
    {
        return summary;
    }

    public void setSummary (String summary)
    {
        this.summary = summary;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summary = "+summary+", data = "+data+", icon = "+icon+"]";
    }
}
