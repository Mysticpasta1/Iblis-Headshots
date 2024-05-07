package iblis_headshots.item;

import net.minecraft.util.*;
import com.google.gson.stream.*;
import java.io.*;
import it.unimi.dsi.fastutil.objects.*;

public class HelmetsConfig
{
    public static Object2FloatMap<ResourceLocation> HELMETS_REGISTRY;
    
    public static void load() {
        final File folder = new File(".", "config");
        folder.mkdirs();
        final File configFile = new File(folder, "iblis_headshots_helmets_config.json");
        try {
            if (configFile.exists()) {
                readConfigFromJson(configFile);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void readConfigFromJson(final File configFile) throws IOException {
        final JsonReader reader = new JsonReader((Reader)new FileReader(configFile));
        reader.setLenient(true);
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            String item = null;
            int protectionPercents = 0;
            while (reader.hasNext()) {
                final String key = reader.nextName();
                if (key.equals("item")) {
                    item = reader.nextString();
                }
                else if (key.equals("protection")) {
                    protectionPercents = reader.nextInt();
                }
                else {
                    reader.skipValue();
                }
            }
            if (item != null) {
                HelmetsConfig.HELMETS_REGISTRY.put(new ResourceLocation(item), 1.0f - protectionPercents / 100.0f);
            }
            reader.endObject();
        }
        reader.endArray();
        reader.close();
    }
    
    static {
        HelmetsConfig.HELMETS_REGISTRY = (Object2FloatMap<ResourceLocation>)new Object2FloatOpenHashMap();
    }
}
