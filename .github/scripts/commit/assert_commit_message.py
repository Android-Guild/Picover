import re
import sys

developer_pattern = re.compile(r'#[0-9]+\s[^\s].*')
renovate_pattern = re.compile(r'Update .+')


def assert_commit_message(branch_name):
    return bool(developer_pattern.fullmatch(branch_name) or renovate_pattern.fullmatch(branch_name))


current_commit_message = sys.argv[1]
commits = [
    (True, "#100 Configure"),
    (True, "#100 Configure kotest"),
    (True, "#100 Configure-kotest"),
    (True, "#100 Configure-kotest"),
    (False, "100 Configure"),
    (False, " #100 Configure"),
    (False, "#100  Configure"),
    (True, "Update kotlin from 1.8.0 to 1.9.0"),
    (False, " Update kotlin"),
    (True, current_commit_message),
]

for is_valid, commit in commits:
    assert is_valid == assert_commit_message(commit), f'Commit message "{commit}" expected to be valid={is_valid}'
