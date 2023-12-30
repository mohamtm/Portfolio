"""Command-line (script) interface to instapy"""
from __future__ import annotations

import argparse
import sys

import numpy as np
from PIL import Image

from . import io
from . import get_filter
from in3110_instapy.timing import time_one

def run_filter(
    file: str,
    out_file: str = None,
    implementation: str = "python",
    filter: str = "color2gray",
    scale: int = 1,
    tuning: float = 1.0,
    runtime: bool = False,
) -> None:
    """Run the selected filter"""

    # loading the image from a file
    image = Image.open(file)

    # resizing if needed
    if scale != 1:
        image = image.resize((image.width // scale, image.height // scale))

    # Applying the filter
    # the sepia tuning only comes into play
    # if the implementation is numpy
    chosen_filter = get_filter(filter, implementation)
    if implementation == "numpy":
        filtered = chosen_filter(np.asarray(image), tuning)
    else:
        filtered = chosen_filter(np.asarray(image))

    # If out file specified
    # saves image else displays file
    if out_file:
        io.write_image(filtered, out_file)
    else:
        # not asked to save, display it instead
        io.display(filtered)
    
    # Finding avg time of 3 calls and printing out
    if runtime:
        avg_time = time_one(chosen_filter, np.asarray(image))
        print(f"\nAverage time over 3 runs: {avg_time}s\n")
    


def main(argv=None):
    """Parse the command-line and call run_filter with the arguments"""
    if argv is None:
        argv = sys.argv[1:]

    parser = argparse.ArgumentParser(prog="in3110_instapy")

    # Creating a group, so user can only input gray or sepia
    filter_group = parser.add_mutually_exclusive_group()

    # the required argument
    parser.add_argument("file", help="The filename to apply filter to")

    # Adding options
    parser.add_argument("-o", "--out", nargs=1 ,help="The output filename")
    filter_group.add_argument("-g", "--gray", action="store_true", help="Select gray filter")
    filter_group.add_argument("-se", "--sepia", nargs=1, type=float, help="Select sepia filter")
    parser.add_argument("-sc", "--scale", nargs=1, type=int ,help="scale factor to resize image(reduces by factor given)")
    parser.add_argument("-i", "--implementation", nargs=1 ,choices=["python", "numba", "numpy"],
                        help="Select implementation of filter")
    parser.add_argument("-r", "--runtime", action="store_true", help="Show average runtime over three runs")    
    
    # parsing arguments and calling run_filter
    args = parser.parse_args()

    filename = args.file
    out = None

    if args.out:
        out = args.out[0]

    filter = "color2gray"
    tuning = 1.0
    if args.sepia:
        filter = "color2sepia"
        tuning = float(args.sepia[0])
        if not 0 <= tuning <= 1:
            parser.error("The tuning factor needs to be between 0 and 1")

    scale = 1
    if args.scale:
        scale = int(args.scale[0])
        if scale == 0:
            parser.error("0 is invalid scaling factor")

    implementation = "python"
    if args.implementation:
        implementation = args.implementation[0]
    
    runtime = args.runtime

    run_filter(filename, out, implementation, filter,
               scale, tuning, runtime)