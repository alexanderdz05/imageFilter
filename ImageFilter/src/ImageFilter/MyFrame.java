package ImageFilter;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;

public class MyFrame extends JFrame implements ActionListener {
	
	// The components of the JFrame:
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel;
    	private JLabel descriptionLabel;
	private JButton openImageButton;
	private JPanel imagePanel;
	private JLabel filterLabel;
	private JComboBox<String> filterComboBox;
	private JButton applyFilterButton;
	private JButton downloadButton;
	private ImageIcon selectedImage;
    
	// The aspect ratio of 16:9 for the imagePanel component:
	private final int ASPECT_RATIO_WIDTH = 16;
	private final int ASPECT_RATIO_HEIGHT = 9;
	
	MyFrame() {
		// Frame
		this.setTitle("Image Filter");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setResizable(false); 
		this.getContentPane().setBackground(Color.CYAN);
		this.setLayout(null);
		
		// Title label
		titleLabel = new JLabel("Image Filter");
        	titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        	titleLabel.setBounds(350, 5, 200, 30);
        	this.add(titleLabel);
        
        	// Description label
        	descriptionLabel = new JLabel("<html> Welcome to Image Filter! To get started, please click on 'Open Image', select an image, and then select a filter <br> "
        		+ "to be applied. Upon selection, you may click on 'Apply Filter' and see your new image! "
        		+ "Finally, you may overlap filters (wouldn't recommend for some) or download your image by pressing 'Download'. Have fun! </html>");
        	descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        	descriptionLabel.setBounds(10, 40, 800, 55);
        	this.add(descriptionLabel);
        
	        // Open image button
	        openImageButton = new JButton("Open Image");
	        openImageButton.setBounds(365, 100, 110, 30);
	        openImageButton.setFocusable(false);
	        openImageButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        openImageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        openImageButton.addActionListener(this);
	        this.add(openImageButton);
        
	        // Image panel
	        int panelWidth = 600;
	        int panelHeight = panelWidth * ASPECT_RATIO_HEIGHT / ASPECT_RATIO_WIDTH;
	        int panelX = 100 + (600 - panelWidth) / 2;
	        int panelY = 175 + (250 - panelHeight) / 2;
	        imagePanel = new JPanel();
	        imagePanel.setBounds(panelX, panelY, panelWidth, panelHeight);
	        imagePanel.setBackground(Color.white);
	        this.add(imagePanel);
        
	        // Filter label
	        filterLabel = new JLabel("Select Filter:");
	        filterLabel.setFont(new Font("Arial", Font.PLAIN, 16));
	        filterLabel.setBounds(100, 480, 100, 20);
	        this.add(filterLabel);
        
	        // Filter combo box
	        filterComboBox = new JComboBox<>();
	        filterComboBox.addItem("Grayscale");
	        filterComboBox.addItem("Blur");
	        filterComboBox.addItem("Brighten");
	        filterComboBox.addItem("Darken");
	        filterComboBox.addItem("Bluescale");
	        filterComboBox.addItem("Greenscale");
	        filterComboBox.addItem("Redscale");
	        filterComboBox.addItem("Yellowscale");
	        filterComboBox.addItem("Tealscale");
	        filterComboBox.addItem("Purplescale");
	        filterComboBox.addItem("Radioactive");
	        filterComboBox.addItem("Static");
	        filterComboBox.addItem("Random");
	        filterComboBox.addItem("Blizzard Special");
	        filterComboBox.setBounds(210, 480, 120, 20);
	        filterComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        this.add(filterComboBox);
        
	        // Apply filter button
	        applyFilterButton = new JButton("Apply Filter");
	        applyFilterButton.setBounds(600, 480, 100, 20);
	        applyFilterButton.setFocusable(false);
	        applyFilterButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        applyFilterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        applyFilterButton.addActionListener(this);
	        this.add(applyFilterButton);
        
	        // Download button
	        downloadButton = new JButton("Download");
	        downloadButton.setBounds(370, 520, 100, 20);
	        downloadButton.setFocusable(false);
	        downloadButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        downloadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        downloadButton.addActionListener(this);
	        this.add(downloadButton);

	        // set visibility 
	        this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			/* Action Performed Method --> void [method]:
			 * First condition: openImageButton: opens the image from the user's computer.
			 * Second condition: applyFilterButton: applies the filter to the selected image.
			 * Third condition: downloadButton: download's the filtered image.
			 */
			
			/* openImageButton --> [actionEvent]:
			 * 1) Creates JFileChooser (fileChooser) object that allows user to select a file from their computer.
			 * 2) fileChooser.setFileFilter() restricts the user to only select images that are in the jpg, jpeg, or png file formats.
			 * 3) Creates an int (result) that awaits user's choice from the user's computer.
			 * 4) if statement checks if the user clicked 'OK' or approved a choice.
			 * 		1) Creates a String (imagePath) that gets the path of the user's selected file.
			 * 		2) uses selectedImage (an imageIcon created outside constructor) and creates the object using imagePath of the image
			 * 		3) seletedImage gets called the resizeImage() method that passes in the selectedImage, the width of the panel, and height of the panel components. More instructions in the method.
			 * 		4) displayImage() method gets called, which displays the image to the imagePanel component.
			 * 5) else statement allows an error to display if the user somehow selects the wrong file type.
			 */
			
			if (e.getSource() == openImageButton) {
				JFileChooser fileChooser = new JFileChooser();
	            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));
	            int result = fileChooser.showOpenDialog(this);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                String imagePath = fileChooser.getSelectedFile().getPath();
	                selectedImage = new ImageIcon(imagePath);
	                selectedImage = resizeImage(selectedImage, imagePanel.getWidth(), imagePanel.getHeight());
	                displayImage(selectedImage);
	            } 
	            else {
	            	imageError();
	            }
			}
			
			/* applyFilterButton --> [actionEvent]:
			 * 1) Creates a String (selectedFilter) that converts the selected option from the comboBox into a String object.
			 * 2) Switch statement for selectedFilter:
			 * 		1) case Grayscale: calls grayScale() method that makes the image gray.
			 * 		2) case Blur: 
			 * 			a) Creates an int (blurScale) and sets it to -1 to start while loop.
			 * 			b) while blurScale is less than 1 and greater than 10 (invalid options): try/catch error
			 * 			c) Try:
			 * 				1) Creates a String (input) and takes the users option.
			 * 				2) if the input is blank (null), break out of the while loop
			 * 				3) convert blurScale option from user to an Integer. 
			 * 				4) if the integer isn't in range, the user must re-enter a value.
			 * 			d) Catch error:
			 * 				1) The user must enter a numeric value.
			 * 			e) if blurScale is in range, call blur() method and pass blurScale to blur the image (see below for instructions).
			 * 		3) case Brighten:
			 * 			a) Creates an int (brightnessScale) and sets it to -1 to start while loop.
			 * 			b) while brightnessScale is less than 1 and greater than 100 (invalid options): try/catch error
			 * 			c) Try:
			 * 				1) Creates a String (input) and takes the users option.
			 * 				2) if the input is blank (null), break out of the while loop
			 * 				3) convert brightnessScale option from user to an Integer. 
			 * 				4) if the integer isn't in range, the user must re-enter a value.
			 * 			d) Catch error:
			 * 				1) The user must enter a numeric value.
			 * 			e) if brightnessScale is in range, call brighten() method and pass brightnessScale to brighten the image (see below for instructions). 
			 * 		4) case Darken:
			 * 			a) Creates an int (darknessScale) and sets it to -1 to start while loop.
			 * 			b) while darknessScale is less than 1 and greater than 100 (invalid options): try/catch error
			 * 			c) Try:
			 * 				1) Creates a String (input) and takes the users option.
			 * 				2) if the input is blank (null), break out of the while loop
			 * 				3) convert darknessScale option from user to an Integer. 
			 * 				4) if the integer isn't in range, the user must re-enter a value.
			 * 			d) Catch error:
			 * 				1) The user must enter a numeric value.
			 * 			e) if darknessScale is in range, call darken() method and pass darknessScale to darken the image (see below for instructions).
			 * 		5) case Bluescale:
			 * 			a) Calls blueScale() method to make the image blue.
			 * 		6) case Greenscale:
			 * 			a) Calls greenScale() method to make the image green.
			 * 		7) case Redscale:
			 * 			a) Calls redScale() method to make the image red.
			 * 		8) case yellowscale:
			 * 			a) Calls yellowScale() method to make the image yellow.
			 * 		9) case Tealscale:
			 * 			a) Calls tealsScale() method to make the image teal.
			 * 		10) case Purplescale:
			 * 			a) Calls purpleScale() method to make the image purple.
			 * 		11) case Radioactive:
			 * 			a) Calls radioactive() method to make the image radioactive.
			 * 		12) case Static:
			 * 			a) Calls staticFilter() method to make the image static.
			 * 		13) case Random:
			 * 			a) Calls randomColor() method to display the image with a random color.
			 * 		14) case Blizzard Special:
			 * 			a) Calls blizzardSpecial() method to give a 'GET HACKED' message and secret clickable link. 
			 * 		15) Default: display message to display that there's an error passing in a filter. 
			 */
			
			else if (e.getSource() == applyFilterButton) {
	            //System.out.println(filterComboBox.getSelectedItem());
	            String selectedFilter = (String) filterComboBox.getSelectedItem();
	            switch (selectedFilter) {
	            	
	            	case "Grayscale":
	            		grayScale();
	            		break;
	            	
	            	case "Blur":
	            		int blurScale = -1;
	            		while (blurScale < 1 || blurScale > 10) {
	            			try {
	            				String input = JOptionPane.showInputDialog("What blur scale would you like? \n 1-10 (1 is minor, 10 is serious)");
	            				if (input == null) {
	            					break;
	            				}
	            				blurScale = Integer.parseInt(input);
	            				if (blurScale < 1 || blurScale > 10) {
	            					JOptionPane.showConfirmDialog(null, "You must enter a valid number between 1 and 10.");
	            				}
	            			} catch (NumberFormatException ex) {
	            				JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.");
	            			}
	            			if (blurScale >=1 && blurScale <= 10) {
	            				blur(blurScale);
	            			}
	            		}
	            		break;
	            		
	            	case "Brighten":
	            		int brightnessLevel = -1;
	            		while (brightnessLevel < 1 || brightnessLevel > 100) {
	            			try {
	            				String input = JOptionPane.showInputDialog("What brightness scale would you like? \n 1-100 (1 is minor, 100 is serious)");
	            				if (input == null) {
	            					break;
	            				}
	            				brightnessLevel = Integer.parseInt(input);
	            				if (brightnessLevel < 1 || brightnessLevel > 100) {
	            					JOptionPane.showConfirmDialog(null, "You must enter a valid number between 1 and 100.");
	            				}
	            			} catch (NumberFormatException ex) {
	            				JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.");
	            			}
	            		}
	            		if (brightnessLevel >=1 && brightnessLevel <= 100) {
	            			brighten(brightnessLevel);
	            		}
	            		break;
	            		
	            	case "Darken":
	            	    int darknessLevel = -1;
	            	    while (darknessLevel < 1 || darknessLevel > 100) {
	            	        try {
	            	            String input = JOptionPane.showInputDialog("What darkness scale would you like? \n 1-100 (1 is minor, 100 is serious)");
	            	            if (input == null) {
	            	                // User clicked cancel or closed the dialog
	            	                break;
	            	            }
	            	            darknessLevel = Integer.parseInt(input);
	            	            if (darknessLevel < 1 || darknessLevel > 100) {
	            	                JOptionPane.showMessageDialog(null, "You must enter a valid number between 1 and 100.");
	            	            }
	            	        } catch (NumberFormatException ex) {
	            	            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.");
	            	        }
	            	    }
	            	    if (darknessLevel >= 1 && darknessLevel <= 100) {
	            	        darken(darknessLevel);
	            	    }
	            	    break;
	            	    
	            	case "Bluescale":
	            		blueScale();
	            		break;
	            	
	            	case "Greenscale":
	            		greenScale();
	            		break;
	            	
	            	case "Redscale":
	            		redScale();
	            		break;
	            	
	            	case "Yellowscale":
	            		yellowScale();
	            		break;
	            		
	            	case "Tealscale":
	            		tealScale();
	            		break;
	            		
	            	case "Purplescale":
	            		purpleScale();
	            		break;
	            		
	            	case "Radioactive":
	            		radioactive();
	            		break;
	            		
	            	case "Static":
	            		staticFilter();
	            		break;
	            		
	            	case "Random":
	            		randomColor();
	            		break;
	            	
	            	case "Blizzard Special":
	            		blizzardSpecial();
	            		break;
	            	
	            	default:
	            		JOptionPane.showMessageDialog(null, "Error while accessing filter. Please select an option");
	            		break;
	            }
	        }
		
		/* downloadButton --> [actionEvent]:
		 * 1) If statement: checks to see if there's an image at selectedImage. If not, else-statement.
		 * 2) Creates a JFileChooser object (fileChooser) that allows user to open the files on their computer.
		 * 3) Creates an int (result) that allows user to save the file.
		 * 4) if the result is approved (it will since it's download):
		 * 5) Creates a File object (outputFile) and gets the selected file from fileChooser that user wants to download.
		 * 6) Creates a String (filePath) that gets the path from the outputFile.
		 * 7) Try:
		 * 		1) Creates a String (fileExtension) that extracts the file extension from filePath. Used to determine the format when saving.
		 * 		2) Creates a File (imageFile) object that uses filePath.
		 * 		3) Creates a BufferedImage object (bufferedImage) Creates a new BufferedImage with the same dimensions as the selected image. The image type is BufferedImage.TYPE_INT_RGB.
		 * 		4) Creates a Graphics2D object (g2d) and created graphics of bufferedImage.
		 * 		5) drawImage() is called on g2d to copy the selected image onto the buffered image.
		 * 		6) dispose() gets rid of g2d. 
		 * 		7) ImageIO.write() is called to save the bufferedImage, with the file extension and the image file. It writes the image into the right format.
		 * 		8) Displays download successful after completing the tasks above.
		 * 8) Catch error: 
		 * 		1) Error while saving the image if the tasks aren't completed is displayed.
		 * Else statement: no image to download in case the user tries to download the blank component.
		 */
		
		else if (e.getSource() == downloadButton) {
			JOptionPane.showMessageDialog(null, "Make sure to select the correct folder you want to download the image onto. \n "
					+ "Please be sure to put '.jpg' or any other image format at the end of the name for your image. \n "
					+ "We highly recommend you put .png or other lossless image format at the end of the name for your image. \n"
					+ "Press 'ok' to continue");
			if (selectedImage != null) {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File outputFile = fileChooser.getSelectedFile();
			    String filePath = outputFile.getAbsolutePath();

			    try {
				String fileExtension = filePath.substring(filePath.lastIndexOf('.') + 1);
				File imageFile = new File(filePath);
				
				// Convert Image to BufferedImage (RenderedImage)
				BufferedImage bufferedImage = new BufferedImage(selectedImage.getIconWidth(), selectedImage.getIconHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bufferedImage.createGraphics();
				g2d.drawImage(selectedImage.getImage(), 0, 0, null);
				g2d.dispose();

				ImageIO.write(bufferedImage, fileExtension, imageFile);
				JOptionPane.showMessageDialog(null, "Download successful!");
			    } catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Error while saving the image: " + ex.getMessage());
			    }
			}
		    } else {
			JOptionPane.showMessageDialog(null, "No image to download");
		    }
		}
	}
	
	/* displayImage(ImageIcon) --> void [method]:
	 * 1) Passes the imageIcon parameter (called when displaying the image into imagePanel component).
	 * 2) Creates a JLabel object (imageLabel) and passes the image to display as a parameter.
	 * 3) removeAll() method is called to imageLabel to remove the previous components that exist.
	 * 4) imagePanel() gets added the new JLabel, imageLabel.
	 * 5) revalidate() method is called to imageLabel to recalculate its layout. 
	 * 6) repaint() method is called to imageLabel to get imagePanel to redraw itself to display the new image.
	 */
	
	private void displayImage(ImageIcon image) {
	        JLabel imageLabel = new JLabel(image);
	        imagePanel.removeAll();
	        imagePanel.add(imageLabel);
	        imagePanel.revalidate();
	        imagePanel.repaint();
    	}
	
	/* resizeImage(ImageICon, int, int) --> ImageIcon [method]:
	 * 1) Passes the selectedImage as the imageIcon, the imagePanel's width, and imagePanel's height to resize the image accordingly with the 16:9 ratio.
	 * 2) Creates an int (imageWidth) to store imageIcon's width.
	 * 3) Creates an int (imageHeight) to store the imageIcon's height.
	 * 4) Creates a double (aspectRatio) to calculate the 16:9 ratio by dividing the width by the height constants (16/9).
	 * 5) Creates a double (imageAspectRatio) to get the image's aspect ratio by dividing the image's width by the height.
	 * 6) Creates two ints (resizedWidth, resizedHeight).
	 * 7) If statement: if the aspect ratio of the image is wider than the aspectRatio (16:9):
	 * 		a) set resizedWidth to the width of the imagePanel.
	 * 		b) set resizedHeight to the quotient of the imagePanel's width by the imageAspectRatio.
	 * 8) Else statement:
	 * 		a) set resizedHeight to the height of the imagePanel.
	 * 		b) set resizedWidth to the product of the imagePanel's height and imageAspectRatio.
	 * 9) Create an Image object (image) and get the image from the selectedImage icon.
	 * 10) Create an Image object (resizedImage) and call getScaledInstance() method to pass the resizedWidth, resizedHeight, and a smooth scale to resize the image.
	 * 11) return: a new ImageIcon containing the resizedImage. */
	
	private ImageIcon resizeImage(ImageIcon imageIcon, int width, int height) {
	        int imageWidth = imageIcon.getIconWidth();
	        int imageHeight = imageIcon.getIconHeight();
	
	        double aspectRatio = (double) ASPECT_RATIO_WIDTH / ASPECT_RATIO_HEIGHT;
	        double imageAspectRatio = (double) imageWidth / imageHeight;
	
	        int resizedWidth, resizedHeight;
	
	        if (imageAspectRatio > aspectRatio) {
	            // Fit image width, adjust height
	            resizedWidth = width;
	            resizedHeight = (int) (width / imageAspectRatio);
	        } else {
	            // Fit image height, adjust width
	            resizedHeight = height;
	            resizedWidth = (int) (height * imageAspectRatio);
	        }
	
	        Image image = imageIcon.getImage();
	        Image resizedImage = image.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
	}
	
	/* imageError() --> void [method]:
	 * 1) Outputs a message saying it's an invalid file type if the user selects it. 
	*/
	
	private void imageError() {
		JOptionPane.showMessageDialog(this, "This is an invalid file type", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/* grayScale() --> void [method]:
	 * 1) Creates an Image object (image) and it gets the selectedImage ImageIcon.
	 * 2) Creates a BufferedImage object (bufferedImage) and passes in the width, height, and the type GRAY of image into parameter.
	 * 3) Creates a Graphics object (graphics) that gets the graphics from bufferedImage. 
	 * 4) graphics gets called drawImage() to draw the bufferedImage over the selectedImage.
	 * 5) graphics gets called dispose() to close itself.
	 * 6) selectedImage becomes bufferedImage with the grayscaled version.
	 * 7) displayImage() method is called to display the new selected image.
	 */
	
	private void grayScale() {
	    Image image = selectedImage.getImage();
	    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
	    Graphics graphics = bufferedImage.getGraphics();
	    graphics.drawImage(image, 0, 0, null);
	    graphics.dispose();
	    selectedImage = new ImageIcon(bufferedImage);
	    displayImage(selectedImage);
	}
	
	/* blur(int) --> void [method]:
	 * 1) Passes an int, blur, which is from the user to customize the blur.
	 * 2) Creates a BufferdImage object (originalImage) and passes into its parameters the selectedImage's width and height as well as the type RGB color format.
	 * 3) Creates a Graphics2D object (g2d) and allows originalImage to be drawn on.
	 * 4) calls drawImage() on g2d to copy the bufferdImage over the selectedImage.
	 * 5) calls dispose() on g2d to close itself.
	 * 6) Creates a float (blurRadius) which assigns the blur from the parameter.
	 * 7) Creates an int (radius) that casts the rounded-up blurRadius from the float.
	 * 8) Creates an int (size) that multiplies the blur (radius) by 2 and adds 1 to determine the area over the blur area applied.
	 * 9) Creates a float (weight) that divideds 1.0f by the product of size^2 to calculate the weight or contribution of each pixel in the blur kernel.
	 * 10) Creates a float[] array (data) which is the size of the product of size^2 to hold the values for the blur kernel.
	 * 11) for loop: 
	 * 		a) for each index in data, update the index to the weight. In ordder for each pixel in the kernel to get the same blur effect.
	 * 12) Creates a Kernel object (kernel) and passes the size, size, and data[] as parameters. Represents the blur kernel that will be applied to the image.
	 * 13) Creates a ConvolveOp object (blurFilter) and passes kernel into constructor. It will apply the blur kernel to the image.
	 * 14) Creates a BufferdImage object (bufferedImage) and assigns it to blurFilter that gets called the filter() method to apply the blur effect to the original image.
	 * 15) selectedImage gets assigned the new imageIcon for blurredImage.
	 * 16) displayImage() is called and gets passed selectedImage to display the blurred image.*/
	
	
	private void blur(int blur) {
	    BufferedImage originalImage = new BufferedImage(selectedImage.getIconWidth(), selectedImage.getIconHeight(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = originalImage.createGraphics();
	    g2d.drawImage(selectedImage.getImage(), 0, 0, null);
	    g2d.dispose();

	    float blurRadius = blur; // Adjust the blur radius as desired

	    int radius = (int) Math.ceil(blurRadius);
	    int size = radius * 2 + 1;
	    float weight = 1.0f / (size * size);
	    float[] data = new float[size * size];
	    for (int i = 0; i < data.length; i++) {
	        data[i] = weight;
	    }

	    Kernel kernel = new Kernel(size, size, data);
	    ConvolveOp blurFilter = new ConvolveOp(kernel);
	    BufferedImage blurredImage = blurFilter.filter(originalImage, null);

	    selectedImage = new ImageIcon(blurredImage);
	    displayImage(selectedImage);
	}
	
	/* brighten(int) --> void [method]:
	 * 1) Passes an int, brightness, into the parameter from the user to hold their brightness strength.
	 * 2) Creates a BufferdImage object (originalImage) and gets the dimensions from the selectedImage as well as RGB color type. 
	 * 3) Creates a Graphics2D object (g2d) and creates the graphics for originalImage.
	 * 4) g2D gets called drawImage() to copy the contents of selectedImage onto originalImage.
	 * 5) g2D gets called dispose() to remove itself.
	 * 6) Creates an int (brightnessLevel), which stores the parameter, brightness, to control the strength of brightness from the user.
	 * 7) Creates a float (scaleFactor), which adds 1 to the quotient of the brightnessLevel by 100. This is done to scale the brightness to ensure the scale factor is always greater than 1.0.
	 * 8) Creates a RescaleOp object (brightenOp) and passes in the scaleFactor, 0, and null as parameters to adjust pixel values in an image.
	 * 9) Creates a BufferedImage object brightenedImage that applies the brightness to originalImage using brightenOp scale. 
	 * 10) selectedImage gets assigned to brightenedImage.
	 * 11) displayImage() is called and passes the new selectedImage to display the brightened image.
	 */
	
	private void brighten(int brightness) {
	    BufferedImage originalImage = new BufferedImage(selectedImage.getIconWidth(), selectedImage.getIconHeight(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = originalImage.createGraphics();
	    g2d.drawImage(selectedImage.getImage(), 0, 0, null);
	    g2d.dispose();

	    int brightnessLevel = brightness; // Adjust the brightness level as desired

	    float scaleFactor = 1.0f + (brightnessLevel / 100.0f);
	    RescaleOp brightenOp = new RescaleOp(scaleFactor, 0, null);
	    BufferedImage brightenedImage = brightenOp.filter(originalImage, null);

	    selectedImage = new ImageIcon(brightenedImage);
	    displayImage(selectedImage);
	}
	
	/* darken(int) --> void [method]:
	 * 1) Passes an int, darkness, into the parameter from the user to hold their darkness strength.
	 * 2) Creates a BufferdImage object (originalImage) and gets the dimensions from the selectedImage as well as RGB color type. 
	 * 3) Creates a Graphics2D object (g2d) and creates the graphics for originalImage.
	 * 4) g2D gets called drawImage() to copy the contents of selectedImage onto originalImage.
	 * 5) g2D gets called dispose() to remove itself.
	 * 6) Creates an int (darknessLevel), which stores the parameter, darkness, to control the strength of darkness from the user.
	 * 7) Creates a float (scaleFactor), which subtracts the quotient of the darknessLevel by 100 from 1. This is done to scale the darkness to ensure the scale factor is always less than 1.0.
	 * 8) Creates a RescaleOp object (darkenOp) and passes in the scaleFactor, 0, and null as parameters to adjust pixel values in an image.
	 * 9) Creates a BufferedImage object darkenedImage that applies the darkness to originalImage using darkenOp scale. 
	 * 10) selectedImage gets assigned to darkenedImage.
	 * 11) displayImage() is called and passes the new selectedImage to display the darkened image. 
	 */
	
	private void darken(int darkness) {
	    BufferedImage originalImage = new BufferedImage(selectedImage.getIconWidth(), selectedImage.getIconHeight(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = originalImage.createGraphics();
	    g2d.drawImage(selectedImage.getImage(), 0, 0, null);
	    g2d.dispose();

	    int darknessLevel = darkness; // Adjust the darkness level as desired

	    float scaleFactor = 1.0f - (darknessLevel / 100.0f);
	    RescaleOp darkenOp = new RescaleOp(scaleFactor, 0, null);
	    BufferedImage darkenedImage = darkenOp.filter(originalImage, null);

	    selectedImage = new ImageIcon(darkenedImage);
	    displayImage(selectedImage);
	}
	
	/* blueScale() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create an int (blue) to hold the blue value of the pixel by extracting the blue component 
	 * 		c) Create an int (newRgb) to hold the value of the blue, without red and green components
	 * 		d) originalImage gets called setRGB() to get the new blue pixel to the pixel.
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the blue image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image into the panel.
	 */
	
	private void blueScale() {
	    Image image = selectedImage.getImage();
	    BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = originalImage.createGraphics();
	    g2d.drawImage(image, 0, 0, null);
	    g2d.dispose();

	    for (int y = 0; y < originalImage.getHeight(); y++) {
	        for (int x = 0; x < originalImage.getWidth(); x++) {
	            int rgb = originalImage.getRGB(x, y);
	            int blue = rgb & 0x0000FF; // Extract the blue component of the pixel
	            int newRgb = blue; // Set red and green components to zero and keep blue component
	            originalImage.setRGB(x, y, newRgb);
	        }
	    }

	    selectedImage = new ImageIcon(originalImage);
	    displayImage(selectedImage);
	}
	
	/* greenScale() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create an int (green) to hold the green value of the pixel by extracting the green component 
	 * 		c) Create an int (newRgb) to hold the value of the green, without red and blue components
	 * 		d) originalImage gets called setRGB() to get the new green pixel to the pixel.
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the green image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image into the panel.
	 */
	
	private void greenScale() {
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		for (int y = 0; y < originalImage.getHeight(); y++) {
			for (int x = 0; x < originalImage.getWidth(); x++) {
				int rgb = originalImage.getRGB(x, y);
				int green = rgb & 0x00FF00;
				int newRgb = green;
				originalImage.setRGB(x, y, newRgb);
			}
		}
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
	/* redScale() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create an int (red) to hold the red value of the pixel by extracting the red component 
	 * 		c) Create an int (newRgb) to hold the value of the red, without green and blue components
	 * 		d) originalImage gets called setRGB() to get the new red pixel to the pixel.
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the red image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image into the panel.
	 */
	
	private void redScale() {
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		for (int y = 0; y < originalImage.getHeight(); y++) {
			for (int x = 0; x < originalImage.getWidth(); x++) {
				int rgb = originalImage.getRGB(x, y);
				int red = rgb & 0xFF0000;
				int newRgb = red;
				originalImage.setRGB(x, y, newRgb);
			}
		}
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
	/* yellowScale() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create an int (yellow) to hold the yellow value of the pixel by extracting the red and green components 
	 * 		c) Create an int (newRgb) to hold the value of the yellow, without blue components
	 * 		d) originalImage gets called setRGB() to get the new yellow pixel to the pixel.
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the yellow image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image into the panel.
	 */
	
	private void yellowScale() {
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		for (int y = 0; y < originalImage.getHeight(); y++) {
			for (int x = 0; x < originalImage.getWidth(); x++) {
				int rgb = originalImage.getRGB(x, y);
				int yellow = rgb & 0xFFFF00;
				int newRgb = yellow;
				originalImage.setRGB(x, y, newRgb);
			}
		}
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
	/* tealScale() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create an int (teal) to hold the teal value of the pixel by extracting the green and blue components
	 * 		c) Create an int (newRgb) to hold the value of the teal, without redcomponent
	 * 		d) originalImage gets called setRGB() to get the new teal pixel to the pixel.
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the teal image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image into the panel.
	 */
	
	private void tealScale() {
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		for (int y = 0; y < originalImage.getHeight(); y++) {
			for (int x = 0; x < originalImage.getWidth(); x++) {
				int rgb = originalImage.getRGB(x, y);
				int teal = rgb & 0x00FFFF;
				int newRgb = teal;
				originalImage.setRGB(x, y, newRgb);
			}
		}
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
	/* purpleScale() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create an int (purple) to hold the purple value of the pixel by extracting the red and blue components
	 * 		c) Create an int (newRgb) to hold the value of the purple, without green component
	 * 		d) originalImage gets called setRGB() to get the new purple pixel to the pixel.
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the purple image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image into the panel.
	 */
	
	private void purpleScale() {
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		for (int y = 0; y < originalImage.getHeight(); y++) {
			for (int x = 0; x < originalImage.getWidth(); x++) {
				int rgb = originalImage.getRGB(x, y);
				int purple = rgb & 0xFF00FF;
				int newRgb = purple;
				originalImage.setRGB(x, y, newRgb);
			}
		}
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
	/* radioactive() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create a Random object (random) 
	 * 		c) Create an int (color) to hold a random int from 0-2
	 * 		d) Conditionals: 0 = red; 1 = green; 2 = blue extracts
	 * 		e) color is set to the extracted color, successfully randomizing each pixel
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the radioactive-like image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image into the panel.
	 */
	
	private void radioactive() {
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		for (int y = 0; y < originalImage.getHeight(); y++) {
			for (int x = 0; x < originalImage.getWidth(); x++) {
				int rgb = originalImage.getRGB(x, y);
				Random random = new Random();
				int color = random.nextInt(2);
				if (color == 0) {
					color = rgb & 0xFF0000;
				} 
				else if (color == 1) {
					color = rgb & 0x00FF00;
				} 
				else if (color == 2) {
					color = rgb & 0x0000FF;
				} 
				originalImage.setRGB(x, y, color);
			}
		}
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
	/* staticFilter() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create a Random object (random)
	 * 		c) Create an int (color) to hold the values 0-7
	 * 		d) Conditional: 0=black; 1=red; 2=green; 3=blue; 4=yellow; 5=teal; 6=purple; 7=white
	 * 		e) color gets assigned to the corresponding color
	 * 		f) originalImage gets called setRGB() to get the new random to the pixel.
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the static-like image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image into the panel.
	 */
	
	private void staticFilter() {
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		for (int y = 0; y < originalImage.getHeight(); y++) {
			for (int x = 0; x < originalImage.getWidth(); x++) {
				int rgb = originalImage.getRGB(x, y);
				Random random = new Random();
				int color = random.nextInt(7);
				if (color == 0) {
					color = rgb & 0x000000;
				}
				else if (color == 1) {
					color = rgb & 0xFF0000;
				}
				else if (color == 2) {
					color = rgb & 0x00FF00;
				}
				else if (color == 3) {
					color = rgb & 0x0000FF;
				}
				else if (color == 4) {
					color = rgb & 0xFFFF00;
				}
				else if (color == 5) {
					color = rgb & 0x00FFFF;
				}
				else if (color == 6) {
					color = rgb & 0xFF00FF;
				}
				else if (color == 7) {
					color = rgb & 0xFFFFFF;
				}
				originalImage.setRGB(x, y, color);
			}
		}
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
	/* randomColor() --> void [method]:
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) dispose() is called on g2d to eliminate itself.
	 * 6) Creates an array of Strings[] (colors) to store the colors
	 * 7) Creates a Random object (random)
	 * 8) Creates an int (colorChoice) to store a random number from 0-5
	 * 6) For each pixel:
	 * 		a) Create an int (rgb) to hold the rgb value of the pixel
	 * 		b) Create an int (color) and set to 0 (temporarily)
	 * 		c) Conditionals: 0=red; 1=green; 2=blue; 3=yellow; 4=teal; 5=purple
	 * 		d) Assign color to the new color
	 * 		e) call setRGB to originalImage to set the value of the new random color
	 * 7) selectedImage gets a new ImageIcon (originalImage)–the random color image.
	 * 8) displayImage() is called and gets passed the selectedImage to display the image with random color into the panel.
	 */
	
	private void randomColor() {
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		String[] colors = {"red", "green", "blue", "yellow", "teal", "purple"};
		Random random = new Random();
		int colorChoice = random.nextInt(colors.length - 1);
		
		for (int y = 0; y < originalImage.getHeight(); y++) {
			for (int x = 0; x < originalImage.getWidth(); x++) {
				int rgb = originalImage.getRGB(x, y);
				int color = 0;
				if (colorChoice == 0) {
					color = rgb & 0xFF0000;
				}
				else if (colorChoice == 1) {
					color = rgb & 0x00FF00;
				}
				else if (colorChoice == 2) {
					color = rgb & 0x0000FF;
				}
				else if (colorChoice == 3) {
					color = rgb & 0xFFFF00;
				}
				else if (colorChoice == 4) {
					color = rgb & 0x00FFFF;
				}
				else if (colorChoice == 5) {
					color = rgb & 0xFF00FF;
				}
				originalImage.setRGB(x, y, color);
			}
		}
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
	/* blizzardSpecial() --> void [method]:
	 * 1) Create a message explaining instructions to the user
	 * 1) Create an Image object (image) and get the image from selectedImage.
	 * 2) Create a BufferedImage object (originalImage) and instantiate it with image's width, height, and type RGB.
	 * 3) Create a Graphics2D object (g2d) on the bufferdImage to create graphics
	 * 4) drawImage() is called on g2d to copy the bufferedImage over the image
	 * 5) setFont() is called to set the font to 'Monospaced', make the font bold with a size of 20
	 * 6) setColor() is called to make the font green
	 * 7) drawString() is called to draw the message, "YOU'VE BEEN HACKED" and align it vertically
	 * 8) dispose() is called on g2d to eliminate itself.
	 * 9) imagePanel gets added a mouseListener:
	 * 		a) mouseClicked(MouseEvent) --> void [method]:
	 * 		b) try: Create a URI object (uri) and pass in my personal website, then allow the user to browse the URL through a browser
	 * 		c) Catch: Errors (ex) and print the stacktrace of the error
	 * 		d) removeMouseListener() is called to imagePanel to only allow the user to click the imagePanel once
	 * 10) selectedImage gets a new ImageIcon (originalImage)–the special image.
	 * 1) displayImage() is called and gets passed the selectedImage to display the special image into the panel.
	 */
	
	private void blizzardSpecial() {
		JOptionPane.showMessageDialog(null, "Click on the link in the image. You may only click once. Run the filter again if an error occured while clicking the link.");
		Image image = selectedImage.getImage();
		BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.setFont(new Font("Monospaced", Font.BOLD, 20));	
		g2d.setColor(Color.green);
		g2d.drawString("YOU'VE BEEN HACKED", 0, imagePanel.getHeight()/2);
		g2d.dispose();
		
		imagePanel.addMouseListener(new MouseAdapter() {
			@Override
		        public void mouseClicked(MouseEvent e) {
		            try {
		                // Specify the URL you want to open
		                URI uri = new URI("https://explorecanadianprovinces.web.app/");
		                // Open the link in the user's default web browser
		                Desktop.getDesktop().browse(uri);
		            } catch (IOException | URISyntaxException ex) {
		                ex.printStackTrace();
		            }
		            imagePanel.removeMouseListener(this);
		        }
        	});
		
		selectedImage = new ImageIcon(originalImage);
		displayImage(selectedImage);
	}
	
}
