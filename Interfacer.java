//import graphic interface libraries
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;
import java.util.Objects;


public class Interfacer {
    String title;
    int height, width;
    JFrame frame;
    GridBagConstraints constraints;
    JPanel panel;

    String pathToImage = "", pathToOutput = "";

    JButton inputBrowseButton, outputBrowseButton, convertButton;
    JLabel widthLabel, inputLabel, outputLabel, resultLabel;
    JTextField inputPathTextField, outputPathTextField;
    JFormattedTextField widthLimitTextField;

    int widthLimit;

    //create window with title and size from constructor
    public Interfacer(String title, int width, int height) {
        //frame stuff
        this.title = title;
        this.width = width;
        this.height = height;
        this.frame = new JFrame(title);
        this.frame.setSize(width, height);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel stuff
        this.panel = new JPanel(new GridBagLayout());
        this.frame.add(this.panel);
        constraints = new GridBagConstraints();
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        instantiateButtons();


        this.frame.setVisible(true);
    }

    //input stuff
    public void makeInputLabel() {
        //add text area to the window
        this.inputLabel = new JLabel("Input file");
        //set size of button
        this.inputLabel.setPreferredSize(new Dimension(100, 20));
        //set the text area to the path of the image
        inputLabel.setText("Input file:");
        //set font size of label
        inputLabel.setFont(new Font("Arial", Font.BOLD, 15));
    }

    public void makeInputBrowseButton() {
        //add browse button to the window
        this.inputBrowseButton = new JButton("Browse");
        //set size of button
        this.inputBrowseButton.setPreferredSize(new Dimension(100, 30));
        //make browse button work
        inputBrowseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(frame);
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                pathToImage = file.getAbsolutePath();
                //set the text area to the path of the image
                inputPathTextField.setText(pathToImage);
                if(Objects.equals(pathToOutput, "")) {
                    pathToOutput = pathToImage.substring(0, pathToImage.lastIndexOf(".")) + ".txt";
                    outputPathTextField.setText(pathToOutput);
                }
            }
        });
    }

    public void makeInputPathTextField(){
        //add text area to the window
        this.inputPathTextField = new JTextField();
        //set size of button
        this.inputPathTextField.setPreferredSize(new Dimension(380, 30));
        //set the text area to the path of the image
        inputPathTextField.setText(pathToImage);
        //set the text area to be uneditable
        inputPathTextField.setEditable(true);
        //add black border to the text area
        inputPathTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    //output stuff
    public void makeOutputLabel() {
        //add text area to the window
        this.outputLabel = new JLabel("Output folder");
        //set size of button
        this.outputLabel.setPreferredSize(new Dimension(100, 20));
        //set the text area to the path of the image
        outputLabel.setText("Output folder:");
        //set font size of label
        outputLabel.setFont(new Font("Arial", Font.BOLD, 15));
    }

    public void makeOutputBrowseButton() {
        //add browse button to the window
        this.outputBrowseButton = new JButton("Browse");
        //set size of button
        this.outputBrowseButton.setPreferredSize(new Dimension(100, 30));
        //make browse button work
        outputBrowseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(frame);
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                pathToOutput = file.getAbsolutePath();

                if(!pathToOutput.endsWith(".txt"))
                    pathToOutput += "\\output.txt";

                //set the text area to the path of the image
                outputPathTextField.setText(pathToOutput);
            }
        });
    }

    public void makeOutputPathTextField(){
        //add text area to the window
        this.outputPathTextField = new JTextField();
        //set size of button
        this.outputPathTextField.setPreferredSize(new Dimension(380, 30));
        //set the text area to the path of the image
        outputPathTextField.setText(pathToOutput);
        //set the text area to be uneditable
        outputPathTextField.setEditable(true);
        //add black border to the text area
        outputPathTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    //label stuff
    public void makeWidthLabel() {
        //add text area to the window
        this.widthLabel = new JLabel("Width Limit");
        //set size of button
        this.widthLabel.setPreferredSize(new Dimension(100, 20));
        //set the text area to the path of the image
        widthLabel.setText("Width Limit:");
        //set font size of label
        widthLabel.setFont(new Font("Arial", Font.BOLD, 15));
    }

    public void makeWidthLimitTextField() {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setAllowsInvalid(false);

        //add text area to the window
        this.widthLimitTextField = new JFormattedTextField(formatter);
        //set size of button
        this.widthLimitTextField.setPreferredSize(new Dimension(100, 20));
        //set the text area to the path of the image
        widthLimitTextField.setText(Integer.toString(widthLimit));
        //set the text area to be uneditable
        widthLimitTextField.setEditable(true);
        //add black border to the text area
        widthLimitTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    //convert button stuff
    public void makeResultLabel() {
        //add text area to the window
        this.resultLabel = new JLabel("");
        //set size of button
        this.resultLabel.setPreferredSize(new Dimension(400, 20));
        //set font size of label
        resultLabel.setFont(new Font("Arial", Font.BOLD, 12));
    }

    public void makeConvertButton() {
        this.convertButton = new JButton("Convert");
        //make convert button work
        convertButton.addActionListener(e -> {
            //get the width limit from the text area
            widthLimit = Integer.parseInt(widthLimitTextField.getText());
            if(widthLimit > 0 && !Objects.equals(pathToOutput, "") && !Objects.equals(pathToImage, "") &&
                (pathToImage.endsWith(".png") || pathToImage.endsWith(".jpg") || pathToImage.endsWith(".jpeg"))) {
                ImageConvertor imageConvertor = new ImageConvertor(pathToImage, pathToOutput, widthLimit);
                imageConvertor.processImage();
                resultLabel.setText("Conversion complete!");
            }
            else {
                if(widthLimit <= 0)
                    resultLabel.setText("Width limit must be greater than 0!");
                else if(Objects.equals(pathToImage, ""))
                    resultLabel.setText("Please select an input file!");
                else if(Objects.equals(pathToOutput, ""))
                    resultLabel.setText("Please select an output folder!");
                else if(!pathToImage.endsWith(".png") || !pathToImage.endsWith(".jpg") || !pathToImage.endsWith(".jpeg"))
                    resultLabel.setText("Please select an image input file!");
            }

        });
    };

    public void instantiateButtons(){

        //input label
        makeInputLabel();

        //browse button
        makeInputBrowseButton();

        //image path text field
        makeInputPathTextField();

        //output label
        makeOutputLabel();

        //browse button
        makeOutputBrowseButton();

        //output path text field
        makeOutputPathTextField();

        //width label
        makeWidthLabel();

        //width limit text field
        makeWidthLimitTextField();

        //result label
        makeResultLabel();

        //convert button
        makeConvertButton();

        //add buttons
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.panel.add(inputLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.panel.add(inputBrowseButton, constraints);
        constraints.gridx = 2;
        constraints.gridy = 0;
        this.panel.add(inputPathTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.panel.add(outputLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        this.panel.add(outputBrowseButton, constraints);
        constraints.gridx = 2;
        constraints.gridy = 1;
        this.panel.add(outputPathTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        this.panel.add(widthLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        this.panel.add(widthLimitTextField, constraints);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        this.panel.add(resultLabel, constraints);
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        this.panel.add(convertButton, constraints);
    }

}
