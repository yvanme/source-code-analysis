/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.sound.sampled;

/**
 * The <code>LineEvent</code> class encapsulates information that a line
 * sends its listeners whenever the line opens, closes, starts, or stops.
 * Each of these four state changes is represented by a corresponding
 * type of event.  A listener receives the event as a parameter to its
 * {@link LineListener#update update} method.  By querying the event,
 * the listener can learn the type of event, the line responsible for
 * the event, and how much data the line had processed when the event occurred.
 *
 * <p>Although this class implements Serializable, attempts to
 * serialize a <code>LineEvent</code> object will fail.
 *
 * @author Kara Kytle
 *
 * @see Line
 * @see LineListener#update
 * @since 1.3
 *
 * @serial exclude
 */
public class LineEvent extends java.util.EventObject {

    // INSTANCE VARIABLES

    /**
     * The kind of line event (<code>OPEN</code>, <code>CLOSE</code>,
     * <code>START</code>, or <code>STOP</code>).
     * @see #getType
     * @serial
     */
    private final Type type;

    /**
     * The media position when the event occurred, expressed in sample frames.
     * Note that this field is only relevant to certain events generated by
     * data lines, such as <code>START</code> and <code>STOP</code>.  For
     * events generated by lines that do not count sample frames, and for any
     * other events for which this value is not known, the position value
     * should be {@link AudioSystem#NOT_SPECIFIED}.
     * @serial
     * @see #getFramePosition
     */
    private final long position;


    /**
     * Constructs a new event of the specified type, originating from the specified line.
     * @param line the source of this event
     * @param type the event type (<code>OPEN</code>, <code>CLOSE</code>, <code>START</code>, or <code>STOP</code>)
     * @param position the number of sample frames that the line had already processed when the event occurred,
     * or {@link AudioSystem#NOT_SPECIFIED}
     *
     * @throws IllegalArgumentException if <code>line</code> is
     * <code>null</code>.
     */
    public LineEvent(Line line, Type type, long position) {

        super(line);
        this.type = type;
        this.position = position;
    }

    /**
     * Obtains the audio line that is the source of this event.
     * @return the line responsible for this event
     */
    public final Line getLine() {

        return (Line)getSource();
    }


    /**
     * Obtains the event's type.
     * @return this event's type ({@link Type#OPEN}, {@link Type#CLOSE},
     * {@link Type#START}, or {@link Type#STOP})
     */
    public final Type getType() {

        return type;
    }

    /**
     * Obtains the position in the line's audio data when the event occurred, expressed in sample frames.
     * For example, if a source line had already played back 14 sample frames at the time it was
     * paused, the pause event would report the line's position as 14.  The next frame to be processed
     * would be frame number 14 using zero-based numbering, or 15 using one-based numbering.
     * <p>
     * Note that this field is relevant only to certain events generated by
     * data lines, such as <code>START</code> and <code>STOP</code>.  For
     * events generated by lines that do not count sample frames, and for any
     * other events for which this value is not known, the position value
     * should be {@link AudioSystem#NOT_SPECIFIED}.
     *
     * @return the line's position as a sample frame number
     */
    /*
     * $$kk: 04.20.99: note to myself: should make sure our implementation is consistent with this.
     * which is a reasonable definition....
     */
    public final long getFramePosition() {

        return position;
    }

    /**
     * Obtains a string representation of the event.  The contents of the string may vary
     * between implementations of Java Sound.
     * @return a string describing the event.
     */
    public String toString() {
        String sType = "";
        if (type != null) sType = type.toString()+" ";
        String sLine;
        if (getLine() == null) {
            sLine = "null";
        } else {
            sLine = getLine().toString();
        }
        return new String(sType + "event from line " + sLine);
    }


    /**
     * The LineEvent.Type inner class identifies what kind of event occurred on a line.
     * Static instances are provided for the common types (OPEN, CLOSE, START, and STOP).
     *
     * @see LineEvent#getType()
     */
    public static class Type {


        /**
         * Type name.
         */
        // $$kk: 03.25.99: why can't this be final??
        private /*final*/ String name;

        /**
         * Constructs a new event type.
         * @param name name of the type
         */
        protected Type(String name) {
            this.name = name;
        }


        //$$fb 2002-11-26: fix for 4695001: SPEC: description of equals() method contains typo
        /**
         * Indicates whether the specified object is equal to this event type,
         * returning <code>true</code> if the objects are identical.
         * @param obj the reference object with which to compare
         * @return <code>true</code> if this event type is the same as
         * <code>obj</code>; <code>false</code> otherwise
         */
        public final boolean equals(Object obj) {
            return super.equals(obj);
        }


        /**
         * Finalizes the hashcode method.
         */
        public final int hashCode() {
            return super.hashCode();
        }


        /**
         * Returns the type name as the string representation.
         */
        public String toString() {
            return name;
        }


        // LINE EVENT TYPE DEFINES

        /**
         * A type of event that is sent when a line opens, reserving system
         * resources for itself.
         * @see #CLOSE
         * @see Line#open
         */
        public static final Type OPEN   = new Type("Open");


        /**
         * A type of event that is sent when a line closes, freeing the system
         * resources it had obtained when it was opened.
         * @see #OPEN
         * @see Line#close
         */
        public static final Type CLOSE  = new Type("Close");


        /**
         * A type of event that is sent when a line begins to engage in active
         * input or output of audio data in response to a
         * {@link DataLine#start start} request.
         * @see #STOP
         * @see DataLine#start
         */
        public static final Type START  = new Type("Start");


        /**
         * A type of event that is sent when a line ceases active input or output
         * of audio data in response to a {@link DataLine#stop stop} request,
         * or because the end of media has been reached.
         * @see #START
         * @see DataLine#stop
         */
        public static final Type STOP   = new Type("Stop");


        /**
         * A type of event that is sent when a line ceases to engage in active
         * input or output of audio data because the end of media has been reached.
         */
        /*
         * ISSUE: we may want to get rid of this.  Is JavaSound
         * responsible for reporting this??
         *
         * [If it's decided to keep this API, the docs will need to be updated to include mention
         * of EOM events elsewhere.]
         */
        //public static final Type EOM  = new Type("EOM");


        /**
         * A type of event that is sent when a line begins to engage in active
         * input or output of audio data.  Examples of when this happens are
         * when a source line begins or resumes writing data to its mixer, and
         * when a target line begins or resumes reading data from its mixer.
         * @see #STOP
         * @see SourceDataLine#write
         * @see TargetDataLine#read
         * @see DataLine#start
         */
        //public static final Type ACTIVE       = new Type("ACTIVE");


        /**
         * A type of event that is sent when a line ceases active input or output
         * of audio data.
         * @see #START
         * @see DataLine#stop
         */
        //public static final Type INACTIVE     = new Type("INACTIVE");

    } // class Type

} // class LineEvent
