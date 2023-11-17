import re
import sys

developer_pattern = re.compile(r'([0-9]+/)?[a-zA-Z0-9-.]+')
renovate_pattern = re.compile(r'renovate/.+')


def assert_branch_name(branch_name):
    return bool(developer_pattern.fullmatch(branch_name) or
                renovate_pattern.fullmatch(branch_name))


current_branch_name = sys.argv[1]
branches = [
    (True, "100/configure"),
    (True, "200/configure-dependabot"),
    (True, "200/bump-kotlin-1.9.0"),
    (True, "renovate/androidx.lifecycle"),
    (True, "100-configure"),
    (True, "100/configure"),
    (True, "configure"),
    (False, "configure branch"),
    (True, current_branch_name),
]
for is_valid, branch in branches:
    assert is_valid == assert_branch_name(branch), f'Branch "{branch}" expected to be valid={is_valid}'
