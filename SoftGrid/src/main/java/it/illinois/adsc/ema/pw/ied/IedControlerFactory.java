package it.illinois.adsc.ema.pw.ied;

import it.illinois.adsc.ema.softgrid.common.ied.IedControlAPI;

/**
 * Created by prageethmahendra on 27/1/2016.
 */
public class IedControlerFactory {
    public static IedControlAPI getPWComBridgeIterface()
    {
        IedControler iedControler = IedControler.getInstance();
        iedControler.init();
        return iedControler;
    }
}
