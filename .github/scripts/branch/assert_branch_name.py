import re
import sys

developer_pattern = re.compile(r'[a-z]{3}/[0-9]+-[a-zA-Z0-9-.]+')
dependabot_pattern = re.compile(r'dependabot/.+')


def assert_branch_name(branch_name):
    return bool(developer_pattern.fullmatch(branch_name) or
                dependabot_pattern.fullmatch(branch_name))


current_branch_name = sys.argv[1]
branches = [
    (True, "rmc/100-configure"),
    (True, "rmc/200-configure-dependabot"),
    (True, "rmc/200-bump-kotlin-1.9.0"),
    (True, "dependabot/gradle/kotlin-1.9.0"),
    (False, "rm/100-configure"),
    (False, "rmcx/100-configure"),
    (True, current_branch_name),
]
for is_valid, branch in branches:
    assert is_valid == assert_branch_name(branch), f'Branch "{branch}" expected to be valid={is_valid}'
