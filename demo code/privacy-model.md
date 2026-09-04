# ADA Privacy Model

ADA follows a privacy-first development model.

## Repository Rules

- Do not commit API keys.
- Do not commit passwords, PINs, OTPs, or access tokens.
- Do not hard-code personal IP addresses.
- Do not commit private device identifiers.
- Do not place credentials in Kotlin source files.
- Use local configuration for development-only secrets.
- Review commits before pushing to a public repository.

## Important Technical Limitation

Logical deletion of a variable or collection does not guarantee physical erasure from device RAM. The MemoryFlush class therefore represents best-effort application-level clearing, not a hardware-secure memory wipe.

Similarly, the SensitiveDataDetector is a heuristic demonstration and should not be treated as a complete security system.
