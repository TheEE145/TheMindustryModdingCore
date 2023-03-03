package net.tmmc.util;

import arc.func.Cons;
import arc.scene.ui.layout.Table;

import mindustry.gen.Icon;
import mindustry.ui.dialogs.BaseDialog;

public class UIUtils {
    public static void invoke(String name, Cons<Table> tableCons) {
        BaseDialog baseDialog = new BaseDialog(name);
        baseDialog.buttons.defaults().size(200, 50);
        baseDialog.cont.pane(tableCons).grow();
        baseDialog.buttons.button("@back", Icon.left, baseDialog::hide);
        baseDialog.show();
    }
}