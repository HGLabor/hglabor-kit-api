package de.hglabor.kitapi.kit.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.platform.Environment;
import de.hglabor.kitapi.platform.JVMLanguage;
import de.hglabor.kitapi.platform.ModdingAPI;

@Environment(
    moddingAPI = ModdingAPI.NONE, // none = no paper api, no fabric api, no mixins etc.
    language = JVMLanguage.JAVA // default is JAVA
)
public class NoneKit extends AbstractKit {



}
