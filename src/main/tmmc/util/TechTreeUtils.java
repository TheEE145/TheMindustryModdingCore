package tmmc.util;

import mindustry.content.TechTree;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.game.Objectives.Objective;
import mindustry.type.ItemStack;

import arc.struct.Seq;
import org.jetbrains.annotations.NotNull;

/**
 * methods from betamindy by sk7725
 * @author sk7725
 */
public class TechTreeUtils {
    public static TechNode context = null;

    public static void margeNode(UnlockableContent parent, @NotNull Runnable children){
        TechNode prev = context;
        context = TechTree.all.find(t -> t.content == parent);
        children.run();
        context = prev;
    }

    public static @NotNull TechNode node(UnlockableContent content,
                                         ItemStack[] requirements,
                                         Seq<Objective> objectives,
                                         Runnable children)
    {
        TechNode node = new TechNode(context, content, requirements);
        if(objectives != null) node.objectives = objectives;

        TechNode prev = context;
        context = node;
        children.run();
        context = prev;

        return node;
    }

    public static @NotNull TechNode node(UnlockableContent content, ItemStack[] requirements, Runnable children) {
        return node(content, requirements, null, children);
    }

    public static @NotNull TechNode node(UnlockableContent content, Seq<Objective> objectives, Runnable children) {
        return node(content, content.researchRequirements(), objectives, children);
    }

    public static @NotNull TechNode node(UnlockableContent content, Seq<Objective> objectives) {
        return node(content, objectives, () -> {});
    }

    public static @NotNull TechNode node(UnlockableContent content, Runnable children) {
        return node(content, content.researchRequirements(), children);
    }

    public static @NotNull TechNode node(UnlockableContent content, ItemStack[] requirements) {
        return node(content, requirements, () -> {});
    }

    public static @NotNull TechNode node(UnlockableContent block) {
        return node(block, () -> {});
    }

    public static @NotNull TechNode nodeProduce(UnlockableContent content,
                                                @NotNull Seq<Objective> objectives,
                                                Runnable children)
    {
        return node(content, content.researchRequirements(), objectives.add(new Objectives.Produce(content)), children);
    }

    public static @NotNull TechNode nodeProduce(UnlockableContent content, Runnable children){
        return nodeProduce(content, Seq.with(), children);
    }

    public static @NotNull TechNode nodeProduce(UnlockableContent content) {
        return nodeProduce(content, Seq.with(), () -> {});
    }

    public static @NotNull TechNode nodeRoot(String name, UnlockableContent content, boolean requireUnlock, Runnable children){
        var root = node(content, content.researchRequirements(), children);
        root.name = name;
        root.requiresUnlock = requireUnlock;
        TechTree.roots.add(root);
        return root;
    }
}