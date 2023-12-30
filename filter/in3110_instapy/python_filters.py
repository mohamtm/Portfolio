"""pure Python implementation of image filters"""
from __future__ import annotations

import numpy as np


def python_color2gray(image: np.array) -> np.array:
    """Convert rgb pixel array to grayscale

    Args:
        image (np.array)
    Returns:
        np.array: gray_image
    """
    # Creating empty array to hold
    # transformed rgb values from image.
    gray_image = np.empty_like(image)

    # Looping through image
    # and applying the changes to the rgb
    for i, dim1 in enumerate(image):
        for j, dim2 in enumerate(dim1):
            weighted_sum = int(dim2[0] * 0.21 + dim2[1] * 0.72
                               + dim2[2] * 0.07)
            gray_image[i][j][0] = weighted_sum
            gray_image[i][j][1] = weighted_sum
            gray_image[i][j][2] = weighted_sum

    return gray_image


def python_color2sepia(image: np.array) -> np.array:
    """Convert rgb pixel array to sepia

    Args:
        image (np.array)
    Returns:
        np.array: sepia_image
    """
    # creating an empty version of image
    # to hold the converted values
    sepia_image = np.empty_like(image)
    
    # Holds the ratios for our rgb values
    # in order red, green and blue.
    sepia_matrix = [
        [0.393, 0.769, 0.189],
        [0.349, 0.686, 0.168],
        [0.272, 0.534, 0.131]
    ]

    # Creating list to hold new rgb values
    rgb = [i for i in range(3)]

    # Loops through image and converts
    # the pixel values in accordance with sepia_matrix
    for dim1 in range(len(image)):
        for dim2 in range(len(image[0])):
            red = image[dim1][dim2][0] * sepia_matrix[0][0] + \
                        image[dim1][dim2][1] * sepia_matrix[0][1] + \
                        image[dim1][dim2][2] * sepia_matrix[0][2]
            
            green = image[dim1][dim2][0] * sepia_matrix[1][0] + \
                        image[dim1][dim2][1] * sepia_matrix[1][1] + \
                        image[dim1][dim2][2] * sepia_matrix[1][2]
            
            blue = image[dim1][dim2][0] * sepia_matrix[2][0] + \
                        image[dim1][dim2][1] * sepia_matrix[2][1] + \
                        image[dim1][dim2][2] * sepia_matrix[2][2]
            
            # We scale the values down so no value exceeds 255
            if max(red,green,blue) > 255:
                scaling_factor = 255 / max(red, green, blue)
                red, green, blue = scaling_factor * red, scaling_factor * green, scaling_factor * blue
            
            # Converting values
            sepia_image[dim1][dim2][0] = int(red)
            sepia_image[dim1][dim2][1] = int(green)
            sepia_image[dim1][dim2][2] = int(blue)

    return sepia_image
