"""numpy implementation of image filters"""
from __future__ import annotations

import numpy as np


def numpy_color2gray(image: np.array) -> np.array:
    """Convert rgb pixel array to grayscale

    Args:
        image (np.array)
    Returns:
        np.array: gray_image
    """

    # Creating empty array to hold
    # transformed rgb values from image.
    gray_image = np.empty_like(image)

    # this line creates a NxMx1 matrix,
    # where image would be a NxMx3 matrix.
    # This matrix contains the weighted sum of the elements in the third dimension.
    weighted_sums = np.dot(image, [0.21, 0.72, 0.07]).reshape((image.shape[0], image.shape[1],1))

    # Changing the rgb according to weighted_sums
    gray_image[:,:] = weighted_sums[:,:]

    # Turning every element into an int
    gray_image = gray_image.astype("uint8")

    # returning the converted image as a np array
    return gray_image


def numpy_color2sepia(image: np.array, k: float = 1) -> np.array:
    """Convert rgb pixel array to sepia

    Args:
        image (np.array)
        k (float): amount of sepia (optional)

    The amount of sepia is given as a fraction, k=0 yields no sepia while
    k=1 yields full sepia.

    (note: implementing 'k' is a bonus task,
        you may ignore it)

    Returns:
        np.array: sepia_image
    """

    # Sepia matrix tuned using linear interpolation
    sepia_matrix = (1-k) * np.identity(3) + k * np.array([
        [0.393, 0.769, 0.189],
        [0.349, 0.686, 0.168],
        [0.272, 0.534, 0.131]
    ])

    # This line gives us the sepia values for each pixel
    sepia_image = np.dot(image, sepia_matrix.T)

    # This line finds the max of the r,g,b for each pixel
    # and outputs the maxes in a NxMx1 matrix, where NxM
    # corresponds to image's NxMx3
    maxes = np.max(sepia_image, axis=2).reshape(image.shape[0],image.shape[1], 1)

    # Scaling the values down the places it is necessary
    sepia_image = np.where(maxes > 255, sepia_image * (255 / maxes), sepia_image)

    # Converting everything to an int.
    sepia_image = sepia_image.astype("uint8")

    return sepia_image
