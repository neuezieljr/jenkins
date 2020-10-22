/*
 * Function for encrypt plaintext to Secret
 */
def toSecret(String plaintext) {
    return hudson.util.Secret.fromString(plaintext)
}
