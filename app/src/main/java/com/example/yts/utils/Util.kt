package com.example.yts.utils


fun getMagnetLink(hash: String): String {
    return "magnet:?xt=urn:btih:$hash&amp;tr=udp://glotorrents.pw:6969/announce&amp;tr=udp://tracker.opentrackr.org:1337/announce&amp;tr=udp://torrent.gresille.org:80/announce&amp;tr=udp://tracker.openbittorrent.com:80&amp;tr=udp://tracker.coppersurfer.tk:6969&amp;tr=udp://tracker.leechers-paradise.org:6969&amp;tr=udp://p4p.arenabg.ch:1337&amp;tr=udp://tracker.internetwarriors.net:1337"
}

