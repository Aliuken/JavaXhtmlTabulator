# XhtmlTabulator
Java file to tabulate a XHTML file (for example, a facelets file from JSF).

The usage of the file is calling:
        XhtmlTabulator.tabulate(originFilePath, destinationFilePath, tabulationUnit);

For example:
        XhtmlTabulator.tabulate("C:/Users/User/Desktop/originFile.xhtml", "C:/Users/User/Desktop/destinationFile.xhtml", "  ");
