package tmmc.powershell;

import arc.Core;
import arc.scene.ui.Dialog;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Cell;
import arc.struct.Seq;
import mindustry.gen.Icon;

public class CommandDialog extends Dialog {
    public CommandEntry entry;
    public String str;

    public CommandDialog(CommandEntry entry) {
        super("@dialog.shell");
        this.entry = entry;
        this.build(true);
    }

    public void build(boolean rebuild) {
        this.clearChildren();

        this.table(table -> {
            Cell<ScrollPane> paneCell = table.pane(logs -> {
                if(entry == null) {
                    logs.labelWrap("[red]NO ENTRY[]").fontScale(2).left();
                } else {
                    Seq<String> log = this.entry.logs;

                    for(int i = 0; i < log.size; i++) {
                        logs.labelWrap(log.get(i)).growX().bottom()
                                .left().padRight(3).row();
                    }
                }
            }).scrollY(true).scrollX(true).grow();

            ScrollPane pane = paneCell.get();
            Core.app.post(() -> {
                pane.setScrollPercentY(1f);
            });
            paneCell.row();

            table.table(cmd -> {
                cmd.button(Icon.ok, () -> {
                    this.entry.execute(this.str == null ? "" : this.str);
                    this.str = "";
                    build(false);
                }).size(48).disabled(ignored -> this.entry == null).pad(6);

                cmd.button(Icon.exit, this::hide).size(48).pad(6);
                cmd.button(Icon.rotate, () -> this.build(false)).size(48).pad(6);

                cmd.field(str, str -> {
                    this.str = str;
                }).growX().pad(6);
            }).growX().bottom();
        }).size(Core.scene.getWidth(), Core.scene.getHeight());

        if(rebuild) {
            build(false);
        }
    }
}