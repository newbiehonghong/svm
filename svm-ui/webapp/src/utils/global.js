function createDownloadElement() {
    let element = document.createElement('a');
    element.style.display = 'none';
    document.body.appendChild(element);
    return element;
}

var downloadElement = null;
export default {
    getDownloadElement: function () {
        if (downloadElement == null) {
            downloadElement = createDownloadElement();
        }
        return downloadElement;
    }
}