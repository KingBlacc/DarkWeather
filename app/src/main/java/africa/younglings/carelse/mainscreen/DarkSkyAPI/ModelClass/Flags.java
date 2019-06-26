package africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass;

public class Flags
{
    private String nearestStation;

    private String[] sources;

    private String units;

    private String meteoalarmLicense;

    public String getNearestStation ()
{
    return nearestStation;
}

    public void setNearestStation (String nearestStation)
{
    this.nearestStation = nearestStation;
}

    public String[] getSources ()
    {
        return sources;
    }

    public void setSources (String[] sources)
    {
        this.sources = sources;
    }

    public String getUnits ()
    {
        return units;
    }

    public void setUnits (String units)
    {
        this.units = units;
    }

    public String getMeteoalarmLicense ()
{
    return meteoalarmLicense;
}

    public void setMeteoalarmLicense (String meteoalarmLicense)
{
    this.meteoalarmLicense = meteoalarmLicense;
}

    @Override
    public String toString()
    {
        return "ClassPojo [nearest-station = "+nearestStation+", sources = "+sources+", units = "+units+", meteoalarm-license = "+meteoalarmLicense+"]";
    }
}
