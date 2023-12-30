from __future__ import annotations

import time
import numpy as np
from typing import Callable
from PIL import Image
from . import get_filter


def time_one(filter_function: Callable, *arguments, calls: int = 3) -> float:
    """Return the time for one call

    When measuring, repeat the call `calls` times,
    and return the average.

    Args:
        filter_function (callable):
            The filter function to time
        *arguments:
            Arguments to pass to filter_function
        calls (int):
            The number of times to call the function,
            for measurement
    Returns:
        time (float):
            The average time (in seconds) to run filter_function(*arguments)
    """
    # Holds the times for each function call
    times = []
    # Looping calls times, timing the filter_function each time
    for i in range(calls):
        t0 = time.perf_counter()
        filter_function(*arguments)
        t1 = time.perf_counter()
        times.append(t1- t0)
    
    # Returning the average
    return sum(times)/len(times)


def make_reports(filename: str = "test/rain.jpg", calls: int = 3):
    """
    Make timing reports for all implementations and filters,
    run for a given image.

    Args:
        filename (str): the image file to use
    """

    # load the image
    image = np.asarray(Image.open(filename))
    # Outputting argument used, path and image size
    # dimensions are given as width x height.
    print(f"\nTiming performed using {filename}: {image.shape[1]}x{image.shape[0]}")
    # iterate through the filters
    filter_names = ["color2gray", "color2sepia"]
    for filter_name in filter_names:
        # get the reference filter function
        reference_filter = get_filter(filter=filter_name)
        # time the reference implementation
        reference_time = time_one(reference_filter, image, calls=calls)
        print(
            f"\nReference (pure Python) filter time {filter_name}: {reference_time:.3}s ({calls=})"
        )
        # iterate through the implementations
        implementations = ["numpy", "numba"]
        for implementation in implementations:
            filter = get_filter(filter=filter_name, implementation=implementation)
            # time the filter
            filter_time = time_one(filter, image, calls=calls)
            # compare the reference time to the optimized time
            speedup = reference_time / filter_time
            print(
                f"Timing: {implementation} {filter_name}: {filter_time:.3}s ({speedup=:.2f}x)"
            )


if __name__ == "__main__":
    # run as `python -m in3110_instapy.timing`
    make_reports()
