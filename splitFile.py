import subprocess

use_file = input("Do you want to use the contents of in.txt? (y/n) ")
if use_file.lower() == 'y':
    with open('in.txt', 'r') as f:
        text = f.read()
else:
    text = input("Please paste your text here: ")

blocks = text.split('\n\n')
output = []
current_block = ""
part_number = 1
part_text = f"\n\n==============END OF PART {part_number}==============\n" + '\n' * 9
for block in blocks:
    if len(block) > 2000:
        subprocess.run(["powershell.exe", "Write-Host 'NOTE: Block length is greater than 2000 characters.' -ForegroundColor Red"])
        sub_blocks = block.split('\n')
        for sub_block in sub_blocks:
            if len(current_block) + len(sub_block) + len(part_text) > 2000:
                output.append(current_block)
                current_block = sub_block
                part_number += 1
                part_text = f"\n\n==============END OF PART {part_number}==============\n" + '\n' * 9
            else:
                current_block += '\n' + sub_block
    else:
        if len(current_block) + len(block) + len(part_text) > 2000:
            output.append(current_block)
            current_block = block
            part_number += 1
            part_text = f"\n\n==============END OF PART {part_number}==============\n" + '\n' * 9
        else:
            current_block += '\n\n' + block
output.append(current_block)

with open('out.txt', 'w') as f:
    for i, block in enumerate(output):
        if i > 0:
            f.write(f"\n\n==============END OF PART {i}==============\n" + '\n' * 9)
        f.write(block)
    f.write(part_text)
