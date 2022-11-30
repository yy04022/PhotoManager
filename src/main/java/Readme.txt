-login username for Adminpage is : admin
-other login username: stock

Testing add photo
-Note: When adding the photo, once you click the addButton,
    you must selected a photo, otherwise the program will have error! Because it stored null photo.
    you could manually clear the photo data in"album.ser" under target folder.
Testing AlbumView PhotoDisplay
-Note: For AlbumViewPage, the maximum photo displayed is only 9 photo, but in the photoview(slideshow),
    it will display all the photo that's being added

Testing Search by Date
-Note: The From and the To date are not included!
    We are using the function from Date class, date.isBefore and date.isAfter

!Do Not clear any data in "user.ser" - This might read file error which lead non functioning loadpage;!
-You can clear the "album.ser"- filepath could not be manually changed on the ser file.
-You can only clear the "album.ser" and "tag.ser";
Do not touch the ser file, because they are seralized.