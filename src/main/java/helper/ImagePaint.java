package helper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import application.ValueHolder;
import data.Plan;
import data.ToolType;
import data.UserElement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tools.Constants;

public class ImagePaint {
	
	public static ImageView paintTempImage(List<UserElement> userElements) {
		Image mapImage = new Image("no-image.jpg");
		ImageView mapImageView = new ImageView();
		try {
			File imageFile = createImageFile(userElements);
			if (imageFile.exists()) {
				mapImage = new Image(imageFile.toURI().toURL().toString());
			}
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
		mapImageView.setImage(mapImage);
		return mapImageView;
	}

	public static File createImageFile(List<UserElement> userElements) {
		File imageFile = null;
		File endFile = getEndFile();
		Plan plan = ValueHolder.INSTANCE.getPlan();
		if (plan.getMap() != null) {
			imageFile = new File(ValueHolder.INSTANCE.getImagePrefix() + plan.getMap());
			copyImage(imageFile, endFile);
			paintUserElements(endFile, userElements);
		}
		return endFile;
	}

	private static File getEndFile() {
		File endFile = new File("image.png");
		if(endFile.exists()) {
			endFile.delete();
		}
		return new File("image.png");
	}

	public static void copyImage(File imageFile, File endFile) {
		try {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(imageFile);
				os = new FileOutputStream(endFile);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
			} finally {
				is.close();
				os.close();
			}
		} catch (Exception e) {
			System.out.println("Error in copyImage");
			e.printStackTrace();
		}
	}

	private static void paintUserElements(File endFile, List<UserElement> userElements) {
		for (UserElement element : userElements) {
			mergeImage(endFile, element);
		}
	}

	private static void mergeImage(File source, UserElement toAdd) {
		try {
			BufferedImage image = ImageIO.read(source);
			BufferedImage overlay = ImageIO.read(createFileFromUserElement(toAdd));

			// create the new image, canvas size is the max. of both image sizes
			int w = Math.max(image.getWidth(), overlay.getWidth());
			int h = Math.max(image.getHeight(), overlay.getHeight());
			BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			// paint both images, preserving the alpha channels
			Graphics g = combined.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.drawImage(overlay, (int) toAdd.getLeft(), (int) toAdd.getTop(), null);
			// Save as new image
			ImageIO.write(combined, "png", source);
		} catch (IOException e) {
			System.out.println("Error in Merger");
			e.printStackTrace();
		}
	}

	private static File createFileFromUserElement(UserElement element) {
		File toMerge = null;
		try { 
			switch (ToolType.getToolType(element.getType())) {
			case HYDRANTH:
				ClassLoader classLoader = ImagePaint.class.getClassLoader();
				toMerge = new File(ValueHolder.INSTANCE.getUserDir()+"hydrant.png");
			case CAR:
				break;
			case IMAGE:
				toMerge = new File(ValueHolder.INSTANCE.getUserImagePrfix()+element.getImage());
				break;
			}
		} catch (Exception e) {
			System.out.println("Error in createFileFromUserElement ");
			e.printStackTrace();
		}
		return toMerge;
	}
	
	public static void resizeImage(UserElement userElement) throws IOException {
		File f = createFileFromUserElement(userElement);
		BufferedImage userImage = ImageIO.read(f);
		BufferedImage scaledImage = Scalr.resize(userImage,Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH, (int)userElement.getWidth(), (int)userElement.getHeight(),  Scalr.OP_ANTIALIAS);
		String[] split = f.getName().split("\\.");
		String ending = split[split.length-1];
		ImageIO.write(scaledImage, ending, f);
	}
	
}
