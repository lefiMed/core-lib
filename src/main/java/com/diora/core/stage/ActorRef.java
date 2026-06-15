package com.diora.core.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.diora.core.stage.actor.ButtonImage;
import com.diora.core.stage.actor.ChangeValue;
import com.diora.core.stage.actor.CheckBoxImage;
import com.diora.core.stage.actor.Extern;
import com.diora.core.stage.actor.ImageTimer;
import com.diora.core.stage.actor.PageView;
import com.diora.core.stage.object.TileActor;
import com.diora.core.stage.object.costum.TileButtonImage;
import com.diora.core.stage.object.costum.TileChangeValue;
import com.diora.core.stage.object.costum.TileCheckBoxImage;
import com.diora.core.stage.object.costum.TileExtern;
import com.diora.core.stage.object.costum.TileImageTimer;
import com.diora.core.stage.object.costum.TilePage;
import com.diora.core.stage.object.gdx.TileButton;
import com.diora.core.stage.object.gdx.TileCheckBox;
import com.diora.core.stage.object.gdx.TileGroup;
import com.diora.core.stage.object.gdx.TileImage;
import com.diora.core.stage.object.gdx.TileImageButton;
import com.diora.core.stage.object.gdx.TileImageTextButton;
import com.diora.core.stage.object.gdx.TileLabel;
import com.diora.core.stage.object.gdx.TileList;
import com.diora.core.stage.object.gdx.TileProgressBar;
import com.diora.core.stage.object.gdx.TileScrollPane;
import com.diora.core.stage.object.gdx.TileSelectBox;
import com.diora.core.stage.object.gdx.TileSlider;
import com.diora.core.stage.object.gdx.TileSplitPane;
import com.diora.core.stage.object.gdx.TileTable;
import com.diora.core.stage.object.gdx.TileTextButton;
import com.diora.core.stage.object.gdx.TileTextFiled;
import com.diora.core.stage.object.gdx.TileTextTooltip;
import com.diora.core.stage.object.gdx.TileTouchpad;
import com.diora.core.stage.object.gdx.TileTree;
import com.diora.core.stage.object.gdx.TileWindow;

public class ActorRef {

    private final static String buttonImage_name = "buttonImage";
    private final static String button_name = "button";
    private final static String checkBoxImage_name = "checkBoxImage";
    private final static String checkBox_name = "checkBox";
    private final static String extern_name = "extern";
    private final static String label_name = "label";
    private final static String list_name = "list";
    private final static String pageView_name = "page";
    private final static String progressBar_name = "progressBar";
    private final static String scrollPane_name = "scrollPane";
    private final static String slider_name = "slider";
    private final static String table_name = "table";
    private final static String textButton_name = "textButton";
    private final static String group_name = "group";
    private final static String point_name = "point";
    private final static String changeValue_name = "value";
    private final static String textField_name = "textField";
    private final static String imageButton_name = "imageButton";
    private final static String imageTextButton_name = "imageTextButton";
    private final static String tree_name = "tree";
    private final static String splitPane_name = "splitPane";
    private final static String texTooltip_name = "textTooltip";
    private final static String window_name = "window";
    private final static String touchpad_name = "touchpad";
    private final static String selectBox_name = "selectBox";
    private final static String image_name = "image";
    private final static String imageTimer_name = "imageTimer";

    public final static ActorDef buttonImage = new ActorDef(buttonImage_name , TileButtonImage.class);
    public final static ActorDef button = new ActorDef(button_name , TileButton.class);
    public final static ActorDef checkBoxImage = new ActorDef(checkBoxImage_name, TileCheckBoxImage.class);
    public final static ActorDef checkBox = new ActorDef(checkBox_name, TileCheckBox.class);
    public final static ActorDef extern = new ActorDef(extern_name, TileExtern.class);
    public final static ActorDef label = new ActorDef(label_name, TileLabel.class);
    public final static ActorDef list = new ActorDef(list_name, TileList.class);
    public final static ActorDef pageView = new ActorDef(pageView_name, TilePage.class);
    public final static ActorDef progressBar = new ActorDef(progressBar_name, TileProgressBar.class);
    public final static ActorDef scrollPane = new ActorDef(scrollPane_name, TileScrollPane.class);
    public final static ActorDef slider = new ActorDef(slider_name, TileSlider.class);
    public final static ActorDef table = new ActorDef(table_name, TileTable.class);
    public final static ActorDef textButton = new ActorDef(textButton_name, TileTextButton.class);
    public final static ActorDef window = new ActorDef(window_name, TileWindow.class);
    public final static ActorDef group = new ActorDef(group_name, TileGroup.class);
    public final static ActorDef point = new ActorDef(point_name, null);
    public final static ActorDef changeValue = new ActorDef(changeValue_name, TileChangeValue.class);
    public final static ActorDef textField = new ActorDef(textField_name, TileTextFiled.class);
    public final static ActorDef imageButton = new ActorDef(imageButton_name, TileImageButton.class);
    public final static ActorDef imageTextButton = new ActorDef(imageTextButton_name, TileImageTextButton.class);
    public final static ActorDef tree = new ActorDef(tree_name, TileTree.class);
    public final static ActorDef splitPane = new ActorDef(splitPane_name, TileSplitPane.class);
    public final static ActorDef textTooltip = new ActorDef(texTooltip_name, TileTextTooltip.class);
    public final static ActorDef touchpad = new ActorDef(touchpad_name, TileTouchpad.class);
    public final static ActorDef selectBox = new ActorDef(selectBox_name, TileSelectBox.class);
    public final static ActorDef image = new ActorDef(image_name, TileImage.class);
    public final static ActorDef imageTimer = new ActorDef(imageTimer_name, TileImageTimer.class);

    public static Array<ActorDef> actorRefs;

    public static ActorDef layerToTileActor(final String name){
        switch (name) {
            case buttonImage_name: return buttonImage;
            case button_name: return button;
            case label_name: return label;
            case textButton_name : return textButton;
            case table_name : return table;
            case checkBoxImage_name: return checkBoxImage;
            case checkBox_name : return checkBox;
            case progressBar_name: return progressBar;
            case scrollPane_name: return scrollPane;
            case slider_name : return slider;
            case pageView_name: return pageView;
            case list_name: return list;
            case window_name: return window;
            case group_name: return group;
            case extern_name: return extern;
            case point_name: return point;
            case changeValue_name: return changeValue;
            case textField_name: return textField;
            case imageButton_name: return imageButton;
            case imageTextButton_name: return imageTextButton;
            case tree_name: return tree;
            case splitPane_name: return splitPane;
            case texTooltip_name: return textTooltip;
            case touchpad_name: return touchpad;
            case selectBox_name: return selectBox;
            case image_name: return image;
            case imageTimer_name: return imageTimer;
            default: {
                if(actorRefs!=null){
                    for (ActorDef actorDef : actorRefs) {
                        if(actorDef.name.equals(name)){
                            return actorDef;
                        }
                    }
                }
                Gdx.app.error("layerToTileActor","remove or add "+ name+": cause not in ActorRef/n");
                return new ActorDef(null, null);
            }
        }
    }

    public static ActorDef classToTileActor(Class ac){
        if(ac.equals(ButtonImage.class)){
            return buttonImage;
        }else if(ac.equals(Button.class)){
            return button;
        }else if(ac.equals(Label.class)){
            return label;
        }else if(ac.equals(TextButton.class)) {
            return textButton;
        }else if(ac.equals(Table.class)){
            return table;
        }else if(ac.equals(CheckBoxImage.class)){
            return checkBoxImage;
        }else if(ac.equals(CheckBox.class)){
            return checkBox;
        }else if(ac.equals(ProgressBar.class)){
            return progressBar;
        }else if(ac.equals(ScrollPane.class)){
            return scrollPane;
        }else if(ac.equals(Slider.class)){
            return slider;
        }else if(ac.equals(PageView.class)){
            return pageView;
        }else if(ac.equals(List.class)){
            return list;
        }else if(ac.equals(Window.class)){
            return window;
        }else if(ac.equals(Group.class)){
            return group;
        }else if(ac.equals(Extern.class)){
            return extern;
        }else if(ac.equals(ChangeValue.class)){
            return changeValue;
        }else if(ac.equals(TextField.class)){
            return textField;
        }else if(ac.equals(ImageButton.class)){
            return imageButton;
        }else if(ac.equals(ImageTextButton.class)){
            return imageTextButton;
        }else if(ac.equals(Tree.class)){
            return tree;
        }else if(ac.equals(SplitPane.class)){
            return splitPane;
        }else if(ac.equals(TextTooltip.class)){
            return textTooltip;
        }else if(ac.equals(Touchpad.class)){
            return touchpad;
        }else if(ac.equals(SelectBox.class)){
            return selectBox;
        }else if(ac.equals(Image.class)){
            return image;
        }else if(ac.equals(ImageTimer.class)){
            return imageTimer;
        } else{
            Gdx.app.error("classToTileActor","remove or add "+ac.getName()+": cause not in ActorRef");
            return null;
        }
    }

    public static class ActorDef {
        public final String name ;
        public final Class<? extends TileActor> classDef;

        public ActorDef(String name, Class<? extends TileActor> classDef) {
            this.name = name;
            this.classDef = classDef;
        }
    }
}
