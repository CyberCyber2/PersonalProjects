import tkinter as tk
from tkinter import filedialog
import os
import glob
from colorama import Fore, init

# Initialize colorama
init()

root = tk.Tk()
directories = []
output_dir = ""
extensions = []

def select_directories():
    global directories
    directory = filedialog.askdirectory()
    directories.append(directory)
    print(f"Added directory: {directory}")
    print(f"Current directories: {directories}")

def select_output_dir():
    global output_dir
    output_dir = filedialog.askdirectory()
    print(f"Selected output directory: {output_dir}")

def merge_files():
    global directories, output_dir, extensions
    extensions = ext_entry.get().split(',')
    print(f"File extensions: {extensions}")
    output_file = os.path.join(output_dir, "output.txt")
    with open(output_file, "w") as outfile:
        for directory in directories:
            for extension in extensions:
                for file in glob.glob(os.path.join(directory, f"*{extension}")):
                    print(f"Reading file: {file}")
                    with open(file, "r") as infile:
                        content = infile.read()
                        print(f"Successfully read file: {file}")
                        outfile.write(f"\n######[Contents of {os.path.basename(file)}]######\n")
                        outfile.write(content)
                        print(f"Successfully wrote content of {file} to output file")
    print(f"{Fore.RED}Output file saved to: {output_file}{Fore.RESET}")
    os.startfile(output_file)

# Create directory selection button
dir_button = tk.Button(root, text="Add Directory", command=select_directories)
dir_button.pack()

# Create output directory selection button
out_button = tk.Button(root, text="Select Output Directory", command=select_output_dir)
out_button.pack()

# Create file extension entry field
ext_label = tk.Label(root, text="Enter file extensions in CSV format (e.g., .txt,.ejs)")
ext_label.pack()
ext_entry = tk.Entry(root)
ext_entry.pack()

# Create merge files button
merge_button = tk.Button(root, text="Merge Files", command=merge_files)
merge_button.pack()

root.mainloop()
