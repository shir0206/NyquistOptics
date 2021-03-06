package com.denniszabolotny.optarget.Model

object DB {
    lateinit var linePair: LinePair
    lateinit var dimInPixel: DimInPixel
    lateinit var focalLength: FocalLength
    lateinit var fov: Fov
    lateinit var sensorPitch: SensorPitch
    lateinit var targetDRI: TargetDRI
    lateinit var targetRange: TargetRange
    lateinit var detector:Detector
    var targetSizes: HashMap<TargetType, TargetSize> = HashMap<TargetType, TargetSize>();

    fun addTargetSize(tS: TargetSize, type: TargetType) {
        targetSizes.put(type, tS);
    }

    fun initDetector(detSizeH:Int,detSizeW:Int,detPitch:Int){
        detector= Detector(detSizeH,detSizeW,detPitch)
    }
    fun initLinePair(lpDet: Double, lpRec: Double, lpIdent: Double, lpDetObj: Double) {
        linePair = LinePair(lpDet, lpRec, lpIdent, lpDetObj)
    }

    fun initFov(ifov: Double, hfov: Double, vfov: Double) {
        fov = Fov(ifov, hfov, vfov)
    }

    fun initTargetDRI(detection: Double, recognition: Double, identification: Double) {
        targetDRI = TargetDRI(detection, recognition, identification)
    }

    fun initTargetRange(range: Double) {
        targetRange = TargetRange(range)
    }

    fun eraseTargetSizes() {
        targetSizes.clear();
    }
}