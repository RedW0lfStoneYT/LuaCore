---
--- Generated by Luanalysis
--- Created by selen.
--- DateTime: 12/7/2023 8:12 PM
---


function run(args)
    local test = args.test
    local test2 = args.test2

    print(test)
    print(test2)
end

function runFailWithExtraVariable(args)
    local test = args.test
    local test2 = args.test2


    print(test)
    print(test2)
    print(args.event:getEventName())
end

return {
    run = run,
    runFailWithExtraVariable = runFailWithExtraVariable
}