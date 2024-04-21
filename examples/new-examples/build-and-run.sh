docker run --rm -it \
    -w `pwd` \
    -v `pwd`:`pwd` \
    -v `pwd`/imasdb:/root/public/imasdb \
    -e CLASSPATH=`pwd` \
    gitlab.eufus.psnc.pl:5050/containerization/imas/imas-installer/al:DD-3.39.0_AL-5.0.0-UDA-2.7.4-UDA-PLUGIN-1.4.0 \
    bash -c "module load IMAS && javac examples/example001_OpenDatabase.java && java examples.example001_OpenDatabase"
