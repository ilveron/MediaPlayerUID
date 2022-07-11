package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.AudioEqualizer;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import it.unical.sadstudents.mediaplayeruid.utils.MyNotification;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AudioEqualizerController implements Initializable {

    @FXML
    private Button plsReset;
    @FXML
    private Button plsSave;

    @FXML
    private ChoiceBox<String> presetChoiceBox;

    @FXML
    private Slider slider32;

    @FXML
    private Slider slider64;

    @FXML
    private Slider slider125;

    @FXML
    private Slider slider250;

    @FXML
    private Slider slider500;

    @FXML
    private Slider slider1k;

    @FXML
    private Slider slider2k;

    @FXML
    private Slider slider4k;

    @FXML
    private Slider slider8k;

    @FXML
    private Slider slider16k;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider32.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(0).setGain(newValue.doubleValue());
                }
            }
        });

        slider64.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(1).setGain(newValue.doubleValue());
                }
            }
        });

        slider125.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(2).setGain(newValue.doubleValue());
                }
            }
        });

        slider250.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(3).setGain(newValue.doubleValue());
                }
            }
        });

        slider500.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(4).setGain(newValue.doubleValue());
                }
            }
        });

        slider1k.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(5).setGain(newValue.doubleValue());
                }
            }
        });

        slider2k.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(6).setGain(newValue.doubleValue());
                }
            }
        });

        slider4k.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(7).setGain(newValue.doubleValue());
                }
            }
        });

        slider8k.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(8).setGain(newValue.doubleValue());
                }
            }
        });

        slider16k.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                if (Player.getInstance().getMediaPlayer() != null) {
                    Player.getInstance().getMediaPlayer().getAudioEqualizer().getBands().get(9).setGain(newValue.doubleValue());
                }
            }
        });

        for(String text:AudioEqualizer.getInstance().getPresetsNames())
            presetChoiceBox.getItems().add(text);
        presetChoiceBox.setValue(AudioEqualizer.getInstance().getPresetsNames().get(AudioEqualizer.getInstance().getCurrentPresetIndex()));
        presetComboBoxHandler(new ActionEvent());
        presetChoiceBox.setOnAction(this::presetComboBoxHandler);

        SceneHandler.getInstance().scaleTransition(plsReset);
        SceneHandler.getInstance().scaleTransition(plsSave);

        setTooltips();
    }

    private void presetComboBoxHandler(ActionEvent event) {
        switch (presetChoiceBox.getValue()){
            case "Flat":
                AudioEqualizer.getInstance().setCurrentPresetIndex(0);
                break;

            case "Acoustic":
                AudioEqualizer.getInstance().setCurrentPresetIndex(1);
                break;

            case "Electronic":
                AudioEqualizer.getInstance().setCurrentPresetIndex(2);
                break;

            case "Pop":
                AudioEqualizer.getInstance().setCurrentPresetIndex(3);
                break;

            case "Rock":
                AudioEqualizer.getInstance().setCurrentPresetIndex(4);
                break;

            case "Bass Boosted":
                AudioEqualizer.getInstance().setCurrentPresetIndex(5);
                break;

            case "Custom":
                controlsDisable(false);
                AudioEqualizer.getInstance().setCurrentPresetIndex(6);
                break;
        }
        if(presetChoiceBox.getValue() != "Custom")
            controlsDisable(true);

        changePreset(AudioEqualizer.getInstance().getPresetsValues().get(AudioEqualizer.getInstance().getCurrentPresetIndex()));
    }


    private void changePreset(int[] values){
        slider32.setValue(values[0]);
        slider64.setValue(values[1]);
        slider125.setValue(values[2]);
        slider250.setValue(values[3]);
        slider500.setValue(values[4]);
        slider1k.setValue(values[5]);
        slider2k.setValue(values[6]);
        slider4k.setValue(values[7]);
        slider8k.setValue(values[8]);
        slider16k.setValue(values[9]);
    }

    @FXML
    void onReset(ActionEvent event) {
        AudioEqualizer.getInstance().saveCustomPreset(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        changePreset(AudioEqualizer.getInstance().getPresetsValues().get(AudioEqualizer.getInstance().getCurrentPresetIndex()));
        MyNotification.notifyInfo("","Custom EQ preset reset",3);
    }

    @FXML
    void onSave(ActionEvent event) {
        int toSave[] = new int[10];
        toSave[0] = (int)slider32.getValue();
        toSave[1] = (int)slider64.getValue();
        toSave[2] = (int)slider125.getValue();
        toSave[3] = (int)slider250.getValue();
        toSave[4] = (int)slider500.getValue();
        toSave[5] = (int)slider1k.getValue();
        toSave[6] = (int)slider2k.getValue();
        toSave[7] = (int)slider4k.getValue();
        toSave[8] = (int)slider8k.getValue();
        toSave[9] = (int)slider16k.getValue();
        AudioEqualizer.getInstance().saveCustomPreset(toSave);
        MyNotification.notifyInfo("","Custom EQ preset saved",3);
    }

    private void controlsDisable(Boolean status){
        plsReset.setDisable(status);
        plsSave.setDisable(status);
        slider32.setDisable(status);
        slider64.setDisable(status);
        slider125.setDisable(status);
        slider250.setDisable(status);
        slider500.setDisable(status);
        slider1k.setDisable(status);
        slider2k.setDisable(status);
        slider4k.setDisable(status);
        slider8k.setDisable(status);
        slider16k.setDisable(status);
    }

    private void setTooltips(){
        plsSave.setTooltip(new Tooltip("Save the custom EQ preset"));
        plsReset.setTooltip(new Tooltip("Reset the custom EQ preset"));
    }
}
