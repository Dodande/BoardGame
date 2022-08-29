package test.java.main;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;

import resources.ImageLoader;

public class ImageLoaderTest {

	@Test(expected=IllegalArgumentException.class)
	public void testLoad() {
		BufferedImage img = ImageLoader.load("castle.png");
		assertNotNull(img);
		assertEquals(34, img.getWidth());
		assertEquals(32, img.getHeight());
		assertTrue(img == ImageLoader.load("castle.png"));
		assertFalse(img.equals(ImageLoader.load("farm.png")));
		ImageLoader.load("this does not exist");
	}

}
