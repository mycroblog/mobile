#!/usr/bin/env perl
# The rule of ADR-0009 §1 for the hand-written Swift of the iOS app: a screen is laid out by the size classes and the
# size of its container only, never by the device idiom, the orientation, the size of the screen or window, or a
# width fixed in the code. `scripts/check/run.sh ios-layout` passes it the files; it prints each finding as
# `file:line: what; instead` and exits 1 when there is one.
#
# Comments are left out before matching, so a comment may name these APIs; string literals are kept, so a key path
# or a selector written as a string is still found. `#if os(...)` and `#if targetEnvironment(...)` choose the
# platform the code is compiled for, not a device it runs on, and are allowed. `.frame(maxWidth:)` caps a width that
# still follows the container (a readable width), and is allowed; so are a width compared with 0 and the width of a
# gesture's translation, which are not layout breakpoints.
use strict;
use warnings;

my $idiom = 'decide by the width size class (horizontalSizeClass of the environment or of the trait collection) or by the container\'s size instead';
my $orientation = 'the orientation says nothing about the space a scene has: use the size classes or the container\'s size, and support every orientation';
my $screen = 'the screen and the window are not the container: read the container\'s size with GeometryReader, onGeometryChange, containerRelativeFrame or a Layout (readableContentGuide or viewWillTransition(to:with:) in UIKit); for the pixel scale use displayScale';
my $breakpoint = 'a fixed width breaks on a size nobody tested: switch on the width size class, or let ViewThatFits or a Layout choose from the container\'s size';
my $fixed = 'a width fixed in the code does not follow the container: derive it from the container (containerRelativeFrame, GeometryReader), cap it with .frame(maxWidth:), or scale it with @ScaledMetric';

my $number = qr/(?:0*[1-9][\d_]*(?:\.\d+)?|0*\.\d*[1-9]\d*)/;    # a numeric literal other than 0
my $sized = qr/(?:\b\w*[sS]ize|\bbounds|\bframe)\s*\??\s*\.\s*width\b|(?<![.\w])width\b/;

my @rules = (
    [qr/\buserInterfaceIdiom\b|\bUIUserInterfaceIdiom\b|\bUITraitUserInterfaceIdiom\b/, 'the device idiom', $idiom],
    [qr/\bUIDevice\s*\.\s*current\s*\.\s*(?:model|localizedModel)\b|\blocalizedModel\b/, 'the device model', $idiom],
    [qr/\b(?:UI)?DeviceOrientation\w*|\w*[iI]nterfaceOrientation\w*/, 'the orientation', $orientation],
    [qr/\b(?:begin|end)GeneratingDeviceOrientationNotifications\b|\borientationDidChangeNotification\b/, 'the orientation', $orientation],
    [qr/\bUIDevice\s*\.\s*current\s*\.\s*orientation\b|\.\s*is(?:Portrait|Landscape)\b/, 'the orientation', $orientation],
    [qr/\bUIScreen\b/, 'the screen', $screen],
    [qr/\bscreen\s*[?!]?\s*\.\s*(?:bounds|nativeBounds|coordinateSpace|fixedCoordinateSpace|applicationFrame)\b/, 'the screen\'s size', $screen],
    [qr/\b(?:nativeBounds|fixedCoordinateSpace)\b|\bcoordinateSpace\s*\.\s*bounds\b/, 'the screen\'s size', $screen],
    [qr/\bdidUpdate(?:EffectiveGeometry|CoordinateSpace)\b|\beffectiveGeometry\b/, 'the scene\'s geometry', $screen],
    [qr/\b(?:window|keyWindow)\s*[?!]?\s*\.\s*(?:bounds|frame)\b/, 'the window\'s size', $screen],
    [qr/(?:$sized)\s*(?:<=?|>=?|==|!=)\s*$number\b|\b$number\s*(?:<=?|>=?|==|!=)\s*[\w.?!()]*?(?:$sized)/, 'a width compared with a fixed number', $breakpoint],
    [qr/\bswitch\s+[\w.?!()]*\bwidth\b/, 'a switch on a width', $breakpoint],
    [qr/\.\s*frame\s*\([^()]*?\b(?:width|minWidth|idealWidth)\s*:\s*-?$number\b/, 'a fixed width in .frame', $fixed],
    [qr/\bwidthAnchor\s*\.\s*constraint\s*\(\s*(?:equalTo|greaterThanOrEqualTo|lessThanOrEqualTo)Constant\s*:/, 'a fixed width constraint', $fixed],
);

my $failed = 0;
for my $file (@ARGV) {
    open my $in, '<', $file or die "cannot read $file: $!\n";
    local $/;
    my $code = <$in>;
    close $in;
    # Leave out the comments, keeping the strings and the number of lines.
    $code =~ s{("(?:\\.|[^"\\\n])*")|(//[^\n]*|/\*.*?\*/)}{defined $1 ? $1 : "\n" x (my $c = $2) =~ tr/\n//}gse;
    my @findings;
    for my $rule (@rules) {
        my ($pattern, $what, $instead) = @$rule;
        while ($code =~ /$pattern/g) {
            my $line = 1 + (substr($code, 0, $-[0]) =~ tr/\n//);
            my $found = $&;
            $found =~ s/\s+/ /g;
            push @findings, [$line, "check: $file:$line: `$found` lays out by $what, which ADR-0009 §1 forbids: $instead.\n"];
        }
    }
    print STDERR map { $_->[1] } sort { $a->[0] <=> $b->[0] } @findings;
    $failed = 1 if @findings;
}
exit $failed;
