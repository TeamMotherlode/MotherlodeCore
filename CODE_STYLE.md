# Code Style
[...]

Also see `.github/linters/sun_checks.xml` for all Checkstyle rules.

## Rules
- No trailing whitespace
- No multiple consecutive blank lines
- Required blank line before `{` in the same indentation level
- Required blank line before `}` in the same indentation level
- No blank line after `{`
- No blank line before `}`
- Required new line at the end of the file
- Imports:
  - No star import (`*`)
  - No unused or redundant imports
  - Import order: `java`, `javax`, `net.minecraft`, `net.fabricmc`, `motherlode`, then everything else
  - No separation between import groups
- Braces (`{` and `}`) have to be on the end of a line
- Indentation: 4 spaces
- Line wrapping indentation: 4 spaces
- Comments have to be indented the same way the code is
- Required whitespace around operators
  - Except `+`, `-`, `*` and `/`, as they may be more readable without spaces in some cases
- Package name has to start with `motherlode`
- Blank line after a keyword with `(` after it (for example, `if (...)`, `while (...)`), but not for method invocations
