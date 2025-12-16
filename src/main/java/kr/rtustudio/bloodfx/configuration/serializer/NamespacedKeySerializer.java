package kr.rtustudio.bloodfx.configuration.serializer;

import kr.rtustudio.configurate.ConfigurationNode;
import kr.rtustudio.configurate.serialize.SerializationException;
import kr.rtustudio.configurate.serialize.TypeSerializer;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class NamespacedKeySerializer implements TypeSerializer<NamespacedKey> {

    @Override
    public NamespacedKey deserialize(Type type, ConfigurationNode node) throws SerializationException {
        String id = node.getString();
        if (id == null) throw new SerializationException(node, type, "Missing ID");
        id = id.contains(":") ? id : NamespacedKey.MINECRAFT + ":" + id;
        NamespacedKey key;
        try {
            key = NamespacedKey.fromString(id.toLowerCase());
        } catch (Exception e) {
            throw new SerializationException(node, type, "Invalid ID: " + id, e);
        }
        return key;
    }

    @Override
    public void serialize(Type type, @Nullable NamespacedKey obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.set(null);
            return;
        }
        node.set(obj.toString());
    }
}
