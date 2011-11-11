all:
	fsc -cp /usr/share/java/scala-swing.jar test.scala Grid.scala ViewGrid.scala

check-syntax:
	 fsc -cp /usr/share/java/scala-swing.jar -deprecation $(CHK_SOURCES)

lunch:
	scala -cp /usr/share/java/scala-swing.jar FirstSwingApp