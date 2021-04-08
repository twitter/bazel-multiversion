#!/bin/bash -e

rm target/dist-macOS.zip 2> /dev/null || true
rm target/dist-Linux.zip 2> /dev/null || true
cp $HOME/Downloads/dist-macOS.zip target/dist-macOS.zip
cp $HOME/Downloads/dist-Linux.zip target/dist-Linux.zip
cd target/
unzip dist-macOS.zip
chmod +x multiversion
mv multiversion multiversion-x86_64-apple-darwin
unzip dist-Linux.zip
chmod +x multiversion
mv multiversion multiversion-x86_64-pc-linux
cd ..
